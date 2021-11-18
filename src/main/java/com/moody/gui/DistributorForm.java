package com.moody.gui;


import com.moody.blockchain.BusinessType;
import com.moody.blockchain.TransactionRecord;
import com.moody.blockchain.TransactionType;
import com.moody.service.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DistributorForm extends JFrame{

    private JPanel mainPanel;
    private JTextField receiveDrugId;
    private JButton confirmReceiveBtn;
    private JLabel receiveDashboardErrorMsg;
    private JLabel msgLabel;
    private JTextField sendDrugId;
    private JButton confirmSendDashboard;
    private JButton logOutBtn;
    private BlockchainService blockchainService;
    private AuthenticationService authenticationService;

    public DistributorForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = new BlockchainServiceImpl();
        this.authenticationService = new AuthenticationServiceImpl();
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
        confirmSendDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (blockchainService.addTransactionRecord(sendDrugId.getText(), TransactionType.SEND)){
                    msgLabel.setText("Success add a send record.");
                    clearForm();
                }else {
                    msgLabel.setText("Drug id not found.");
                }
            }
        });
        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
                authenticationService.logOut(FormManager.distributorForm);
            }
        });
    }

    public void clearForm(){
        receiveDrugId.setText("");
        sendDrugId.setText("");
    }

}
