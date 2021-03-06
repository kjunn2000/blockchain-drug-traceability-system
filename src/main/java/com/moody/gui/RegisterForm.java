package com.moody.gui;

import com.moody.authentication.User;
import com.moody.blockchain.BusinessType;
import com.moody.service.AuthenticationService;
import com.moody.service.AuthenticationServiceImpl;
import com.moody.service.FormManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class RegisterForm extends JFrame {
    private JPanel mainPanel;
    private JLabel fullNameLabel;
    private JLabel passwordLabel;
    private JLabel companyNameLabel;
    private JLabel companyLocationLabel;
    private JLabel businessTypeLabel;
    private JTextArea companyLocationArea;
    private JTextField fullNameField;
    private JTextField passwordField;
    private JComboBox businessTypeBox;
    private JButton registerBtn;
    private JTextField companyNameField;
    private JLabel errMsg;
    private AuthenticationService authenticationService;

    public RegisterForm(String title, AuthenticationService authenticationService){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.authenticationService = authenticationService;
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fullNameField.getText().isEmpty() ||
                        passwordField.getText().isEmpty() ||
                        companyNameField.getText().isEmpty() ||
                    companyLocationArea.getText().isEmpty() ||
                Objects.isNull(businessTypeBox.getSelectedItem())){
                        errMsg.setText("Please enter all the fields.");
                        return;
                }
                if (!authenticationService.register(new User(fullNameField.getText(),
                        passwordField.getText(),
                        companyNameField.getText(),
                        companyLocationArea.getText(),
                        BusinessType.valueOf((String)businessTypeBox.getSelectedItem())))) {
                    errMsg.setText("Failed to register. Please try again.");
                }else{
                    clearForm();
                }
            }
        });
    }

    public void clearForm(){
        companyLocationArea.setText("");
        fullNameField.setText("");
        passwordField.setText("");
        businessTypeBox.setSelectedIndex(0);
        companyNameField.setText("");
        errMsg.setText("");
    }
}
