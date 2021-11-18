package com.moody.gui;

import com.moody.blockchain.*;
import com.moody.service.*;

import java.math.BigInteger;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierForm extends JFrame{
    private JPanel mainPanel;
    private JTextField quantityField;
    private JTextField drugNameField;
    private JTextField descriptionField;
    private JButton addDrugBtn;
    private JButton logOutButton;
    private BlockchainService blockchainService;
    private AuthenticationService authenticationService;

    public SupplierForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = new BlockchainServiceImpl();
        this.authenticationService = new AuthenticationServiceImpl();
        addDrugBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Drug drug = new Drug(drugNameField.getText(), new BigInteger(quantityField.getText()), descriptionField.getText(), DrugStatus.UNPROCESSED);
                blockchainService.addNewDrug(drug);
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
    }

    public void clearForm(){
        quantityField.setText("");
        drugNameField.setText("");
        descriptionField.setText("");
    }

}
