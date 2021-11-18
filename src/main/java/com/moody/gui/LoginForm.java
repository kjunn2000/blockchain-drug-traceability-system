package com.moody.gui;

import com.moody.service.AuthenticationService;
import com.moody.service.AuthenticationServiceImpl;
import com.moody.service.FormManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JPanel mainPanel;
    private JTextField fullNameField;
    private JButton loginButton;
    private JTextField passwordField;
    private JButton registerButton;
    private JLabel errorMsg;
    private AuthenticationService authenticationService;

    public LoginForm(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        authenticationService = new AuthenticationServiceImpl();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               if(!authenticationService.logIn(fullNameField.getText(), passwordField.getText())) {
                    errorMsg.setText("User not found");
               }else{
                   clearForm();
               }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
                FormManager.closeForm(FormManager.loginForm);
                FormManager.openForm(FormManager.registerForm);
            }
        });
    }

    public void clearForm(){
        fullNameField.setText("");
        passwordField.setText("");
        errorMsg.setText("");
    }
}
