package com.moody.gui;

import com.moody.authentication.UserBank;
import com.moody.blockchain.TransactionRecord;
import com.moody.crypto.ASymmCrypto;
import com.moody.crypto.SymmCrypto;
import com.moody.digitalSignature.DigitalSignature;
import com.moody.digitalSignature.DigitalSignatureImpl;
import com.moody.keygen.KeyAccess;
import com.moody.keygen.SecretCharsKeyGen;
import com.moody.service.BlockchainService;
import com.moody.service.BlockchainServiceImpl;
import com.moody.service.FormManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuestForm extends JFrame {

    private JPanel mainPanel;
    private JTextField drugIdField;
    private JButton trackButton;
    private JLabel errMsg;
    private JTable historyTable;
    private JButton verifySignatureButton;
    private JTextArea digitalSignatureArea;
    private BlockchainService blockchainService;
    private JScrollPane scrollPane;
    private List<TransactionRecord> transactionRecords;

    public GuestForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        blockchainService = new BlockchainServiceImpl();

        setTableData(null);

        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<TransactionRecord> transactionRecordData = blockchainService.getTransactionRecordData(drugIdField.getText());
                if (transactionRecordData == null){
                    errMsg.setText("Drug ID not found.");
                    return;
                }
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
                TransactionRecord selectedRecord = transactionRecords.get(historyTable.getSelectedRow());
                digitalSignatureArea.setText(selectedRecord.getDigitalSignature());
            }
        });
        verifySignatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DigitalSignature digitalSignature = new DigitalSignatureImpl();
                TransactionRecord selectedRecord = transactionRecords.get(historyTable.getSelectedRow());
                SymmCrypto symmCrypto = new SymmCrypto();
                String filename = "";
                try {
                    filename = symmCrypto.encrypt(selectedRecord.getPersonInCharge(), SecretCharsKeyGen.keygen()).replaceAll("/","");
                } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                    e.printStackTrace();
                }

                try {
                    boolean result = digitalSignature.verify(selectedRecord.toString()
                            , selectedRecord.getDigitalSignature()
                            , KeyAccess.getPublicKey(filename));
                    if (result) {
                        JOptionPane.showMessageDialog(FormManager.guestForm
                                , "Verified Successful!"
                                , "Configurations"
                                , JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(FormManager.guestForm
                                , "Verified Successful!"
                                ,"Alert"
                                ,JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clearForm(){
        drugIdField.setText("");
        errMsg.setText("");
    }

    public void setTableData(String[][] data){
        historyTable.setModel(new DefaultTableModel(data, getHeaders()));
    }

    public String[] getHeaders(){
        return new String[]{"Transaction ID", "Company", "Location", "Person In Charge","Business Type","Type",  "Datetime" };
    }
}
