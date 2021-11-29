package com.moody.gui;

import com.moody.authentication.User;
import com.moody.authentication.UserBank;
import com.moody.blockchain.*;
import com.moody.service.*;

import java.math.BigInteger;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierForm extends JFrame{
    private JPanel mainPanel;
    private JTextField quantityField;
    private JTextField drugNameField;
    private JTextField descriptionField;
    private JButton addDrugBtn;
    private JButton logOutButton;
    private JTextField unitPriceField;
    private JLabel errMsg;
    private JButton trackingDashboardButton;
    private JLabel businessTypeLabel;
    private JComboBox manufacturerBox;
    private BlockchainService blockchainService;
    private AuthenticationService authenticationService;
    private List<User> manufacturerList;

    public SupplierForm(String title, BlockchainService blockchainService, AuthenticationService authenticationService) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = blockchainService;
        this.authenticationService = authenticationService;
        fetchManufacturerList();
        addDrugBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double unitPrice = null;
                BigInteger quantity = null;
                try {
                    unitPrice = Double.valueOf(unitPriceField.getText());
                    quantity = new BigInteger(quantityField.getText());
                }catch (Exception e){
                    errMsg.setText("Invalid input.");
                    return;
                }
                Optional<User> manufacturer = manufacturerList.stream().filter(m -> m.getFullName().equals(manufacturerBox.getSelectedItem())).findFirst();
                if (manufacturer.isEmpty()){
                    errMsg.setText("Invalid input.");
                    return;
                }
                Drug drug = new Drug(drugNameField.getText(),
                        quantity,
                        String.valueOf(unitPrice),
                        descriptionField.getText(),
                        DrugStatus.UNPROCESSED);
                if (!blockchainService.addNewDrug(drug, manufacturer.get())) {
                    errMsg.setText("Fail to add new drug.");
                };
                clearForm();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
                authenticationService.logOut(FormManager.supplierForm);
            }
        });
        trackingDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FormManager.switchToTrackingDashboard(FormManager.supplierForm);
            }
        });
    }

    public void clearForm(){
        quantityField.setText("");
        unitPriceField.setText("");
        drugNameField.setText("");
        descriptionField.setText("");
        errMsg.setText("");
    }

    public void fetchManufacturerList(){
        List<User> allManufacturer = UserBank.getAllManufacturer();
        if (Objects.isNull(allManufacturer)){
            return;
        }else{
            this.manufacturerList = allManufacturer;
        }
        this.manufacturerBox.setModel(new DefaultComboBoxModel(manufacturerList.stream().map(User::getFullName).toArray()));
    }

}
