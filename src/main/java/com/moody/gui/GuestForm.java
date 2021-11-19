package com.moody.gui;

import javax.swing.*;
import java.awt.*;

public class GuestForm extends JFrame {

    private JPanel mainPanel;
    private JTextField drugIdField;
    private JTable trackingTable;
    private JButton trackButton;

    public GuestForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }
}
