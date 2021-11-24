package com.moody.gui;

import com.moody.authentication.User;
import com.moody.authentication.UserBank;
import com.moody.blockchain.Block;
import com.moody.blockchain.BusinessType;
import com.moody.blockchain.Drug;
import com.moody.blockchain.TransactionRecord;
import com.moody.service.BlockchainService;
import com.moody.service.FormManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrackingForm extends JFrame {

    private JPanel mainPanel;
    private JTextField drugIdField;
    private JButton trackButton;
    private JLabel errMsg;
    private JTable historyTable;
    private JButton verifySignatureButton;
    private JTextArea digitalSignatureArea;
    private JButton backBtn;
    private JTextField quantityField;
    private JTextField drugNameField;
    private JTextField descriptionField;
    private JTextField unitPriceField;
    private JTextField statusField;
    private BlockchainService blockchainService;
    private JScrollPane scrollPane;
    private List<TransactionRecord> transactionRecords;

    public TrackingForm(String title, BlockchainService blockchainService) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = blockchainService;

        setTableData(null);

        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Block block = blockchainService.findBlock(drugIdField.getText());
                List<TransactionRecord> transactionRecordData = sortTransactionData(block);

                if (transactionRecordData == null){
                    errMsg.setText("Drug ID not found.");
                    return;
                }

                Drug drug = block.getDrug();
                drugNameField.setText(drug.getName());
                quantityField.setText(drug.getQuantity().toString());
                descriptionField.setText(drug.getDescription());
                User user = UserBank.getCurrentUser();
                unitPriceField.setText(drug.getUnitPrice());
                if(Objects.nonNull(user) && user.getBusinessType().equals(BusinessType.MANUFACTURER)){
                    String decryptUnitPrice = blockchainService.decryptUnitPrice(drug.getUnitPrice());
                    unitPriceField.setText(decryptUnitPrice);
                }
                statusField.setText(drug.getStatus().toString());

                transactionRecords = transactionRecordData;
                String[][] data = new String[transactionRecordData.size()][7];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for (int i = 0 ; i < transactionRecordData.size() ; i ++){
                    data[i][0] = transactionRecordData.get(i).getTransactionId().toString();
                    data[i][1] = transactionRecordData.get(i).getCompanyName();
                    data[i][2] = transactionRecordData.get(i).getCompanyLocation();
                    data[i][3] = transactionRecordData.get(i).getPersonInCharge();
                    data[i][4] = transactionRecordData.get(i).getBusinessType().toString();
                    data[i][5] = transactionRecordData.get(i).getType().toString();
                    data[i][6] = transactionRecordData.get(i).getDateTime().format(formatter);
                }
                setTableData(data);
            }
        });
        verifySignatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        historyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(historyTable.getSelectedRow() < 0 || historyTable.getSelectedRow() >= transactionRecords.size()){
                    return;
                }
                TransactionRecord selectedRecord = transactionRecords.get(historyTable.getSelectedRow());
                digitalSignatureArea.setText(selectedRecord.getDigitalSignature());
            }
        });
        verifySignatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                boolean result = blockchainService.verifyDigitalSignature(transactionRecords.get(historyTable.getSelectedRow()));
                if (result) {
                    JOptionPane.showMessageDialog(FormManager.trackingForm
                            , "Verified Successful!"
                            , "Congratulations"
                            , JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(FormManager.trackingForm
                            , "Verified Fail!"
                            ,"Alert"
                            ,JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
                FormManager.closeForm(FormManager.trackingForm);
                User currentUser = UserBank.getCurrentUser();
                if(!Objects.nonNull(currentUser)){
                    FormManager.openForm(FormManager.loginForm);
                }else if (currentUser.getBusinessType() == BusinessType.SUPPLIER){
                    FormManager.openForm(FormManager.supplierForm);
                }else if (currentUser.getBusinessType() == BusinessType.MANUFACTURER){
                    FormManager.openForm(FormManager.manufacturerForm);
                }else if (currentUser.getBusinessType() == BusinessType.PHARMACY){
                    FormManager.openForm(FormManager.phamarcyForm);
                }
        }
        });
    }

    public List<TransactionRecord> sortTransactionData(Block block) {
        return block.getTranx().getTranxLst().stream()
                .sorted(Comparator.comparing(TransactionRecord::getDateTime))
                .collect(Collectors.toList());
    }

    public void clearForm(){
        drugIdField.setText("");
        errMsg.setText("");
        setTableData(null);
        digitalSignatureArea.setText("");
        drugNameField.setText("");
        quantityField.setText("");
        descriptionField.setText("");
        unitPriceField.setText("");
        statusField.setText("");
    }

    public void setTableData(String[][] data){
        historyTable.setModel(new DefaultTableModel(data, getHeaders()));
    }

    public String[] getHeaders(){
        return new String[]{"Transaction ID", "Company", "Location", "Person In Charge","Business Type","Type",  "Datetime" };
    }
}
