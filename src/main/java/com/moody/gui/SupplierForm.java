package com.moody.gui;

import com.moody.blockchain.*;

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

    public SupplierForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        addDrugBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Drug drug = new Drug(drugNameField.getText(), new BigInteger(quantityField.getText()), descriptionField.getText(), DrugStatus.UNPROCESSED);
                Block prevBlock = Blockchain.get().getLast();
                Block newBlock = new Block( prevBlock.getHeader().getCurrentHash(), drug);
                newBlock.getHeader().setIndex(prevBlock.getHeader().getIndex()+1);
                Transaction newTranx = new Transaction();
                newTranx.add(new TransactionRecord(TransactionType.SEND,
                        "Tam Kai Jun",
                        "Moody Co",
                        "Bukit Jalil",
                        BusinessType.SUPPLIER));
                newBlock.setTranx(newTranx);
                Blockchain.nextBlock( newBlock);
                Blockchain.distribute();

                quantityField.setText("");
                drugNameField.setText("");
                descriptionField.setText("");
            }
        });
    }

}
