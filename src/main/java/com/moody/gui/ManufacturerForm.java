package com.moody.gui;

import com.moody.blockchain.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ManufacturerForm extends JFrame{

    private JPanel mainPanel;
    private JTextField receiveDrugId;
    private JButton confirmReceiveBtn;
    private JLabel receiveDashboardErrorMsg;
    private JLabel sendDashboardErrorMsg;
    private JTextField sendDrugId;
    private JButton confirmSendDashboard;

    public ManufacturerForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        confirmReceiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Block target = null;
                for (Block block : Blockchain.DB){
                    if(Objects.isNull(block.getDrug())){
                        continue;
                    }
                    if (block.getDrug().getDrugId().toString().equals(receiveDrugId.getText())){
                        target = block;
                        break;
                    }
                }
                if (Objects.isNull(target)){
                    receiveDashboardErrorMsg.setText("Invalid Drug ID");
                    return;
                }
                Transaction newTranx = target.getTranx();
                newTranx.add(new TransactionRecord(TransactionType.RECEIVED,
                        "Chai Juo Ann",
                        "Moody Co",
                        "Bukit Jalil",
                        BusinessType.MANUFACTURER));
                target.setTranx(newTranx);
                Blockchain.persist();
                Blockchain.distribute();
            }
        });
        confirmSendDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Block target = null;
                for (Block block : Blockchain.DB){
                    if(Objects.isNull(block.getDrug())){
                        continue;
                    }
                    if (block.getDrug().getDrugId().toString().equals(sendDrugId.getText())){
                        target = block;
                        break;
                    }
                }
                if (Objects.isNull(target)){
                    sendDashboardErrorMsg.setText("Invalid Drug ID");
                    return;
                }
                Transaction newTranx = target.getTranx();
                newTranx.add(new TransactionRecord(TransactionType.SEND,
                        "Chai Juo Ann",
                        "Moody Co",
                        "Bukit Jalil",
                        BusinessType.MANUFACTURER));
                target.setTranx(newTranx);
                Blockchain.persist();
                Blockchain.distribute();
            }
        });
    }

}
