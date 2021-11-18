package com.moody.gui;

import com.moody.blockchain.BusinessType;
import com.moody.blockchain.TransactionRecord;
import com.moody.blockchain.TransactionType;
import com.moody.service.BlockchainService;
import com.moody.service.BlockchainServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManufacturerForm extends JFrame{

    private JPanel mainPanel;
    private JTextField receiveDrugId;
    private JButton confirmReceiveBtn;
    private JLabel receiveDashboardErrorMsg;
    private JLabel msgLabel;
    private JTextField sendDrugId;
    private JButton confirmSendDashboard;
    private BlockchainService blockchainService;

    public ManufacturerForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.blockchainService = new BlockchainServiceImpl();
        confirmReceiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TransactionRecord transactionRecord = new TransactionRecord(TransactionType.RECEIVED,
                        "Chai Juo Ann",
                        "Moody Co",
                        "Bukit Jalil",
                        BusinessType.MANUFACTURER);
                if (blockchainService.addTransactionRecord(receiveDrugId.getText(), transactionRecord)){
                    msgLabel.setText("Success add a receive record.");
                }else {
                    msgLabel.setText("Drug id not found.");
                }
            }
        });
        confirmSendDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TransactionRecord transactionRecord = new TransactionRecord(TransactionType.SEND,
                        "Chai Juo Ann",
                        "Moody Co",
                        "Bukit Jalil",
                        BusinessType.MANUFACTURER);
                if (blockchainService.addTransactionRecord(sendDrugId.getText(), transactionRecord)){
                    msgLabel.setText("Success add a send record.");
                }else {
                    msgLabel.setText("Drug id not found.");
                }
            }
        });
    }

}
