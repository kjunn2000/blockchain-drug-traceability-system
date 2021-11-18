package com.moody.service;

import com.moody.authentication.User;

import javax.swing.*;

public interface AuthenticationService {
    boolean logIn(String fullName, String password);
    void logOut(JFrame form);
    boolean register(User user);
}
