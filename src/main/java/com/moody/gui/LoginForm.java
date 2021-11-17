package com.moody.gui;

import javax.swing.*;

public class LoginForm extends JFrame {
    private JPanel mainPanel;

    public LoginForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args){
        LoginForm loginForm = new LoginForm("Drug Traceability System");
        loginForm.setVisible(true);
    }
}
