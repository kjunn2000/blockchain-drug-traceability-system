package com.moody.gui;

import com.moody.blockchain.*;
import com.moody.service.BlockchainService;
import com.moody.service.BlockchainServiceImpl;

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
    private BlockchainService blockchainService;

    public SupplierForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = new BlockchainServiceImpl();
        addDrugBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Drug drug = new Drug(drugNameField.getText(), new BigInteger(quantityField.getText()), descriptionField.getText(), DrugStatus.UNPROCESSED);
                blockchainService.addNewDrug(drug);
                quantityField.setText("");
                drugNameField.setText("");
                descriptionField.setText("");
            }
        });
    }

}
