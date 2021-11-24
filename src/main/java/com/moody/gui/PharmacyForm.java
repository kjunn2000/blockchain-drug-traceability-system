package com.moody.gui;

import com.moody.blockchain.BusinessType;
import com.moody.blockchain.TransactionRecord;
import com.moody.blockchain.TransactionType;
import com.moody.service.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacyForm extends JFrame{

    private JPanel mainPanel;
    private JTextField receiveDrugId;
    private JButton confirmReceiveBtn;
    private JLabel msgLabel;
    private JButton logOutButton;
    private JButton trackingDashboardButton;
    private BlockchainService blockchainService;
    private AuthenticationService authenticationService;

    public PharmacyForm(String title, BlockchainService blockchainService, AuthenticationService authenticationService) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = blockchainService;
        this.authenticationService = authenticationService;

        confirmReceiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (blockchainService.addTransactionRecord(receiveDrugId.getText(), TransactionType.RECEIVED)){
                    msgLabel.setText("Success add a receive record.");
                    clearForm();
                }else {
                    msgLabel.setText("Drug id not found.");
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                authenticationService.logOut(FormManager.phamarcyForm);
                clearForm();
            }
        });
        trackingDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FormManager.switchToTrackingDashboard(FormManager.manufacturerForm);
            }
        });
    }

    public void clearForm(){
        receiveDrugId.setText("");
    }

}
