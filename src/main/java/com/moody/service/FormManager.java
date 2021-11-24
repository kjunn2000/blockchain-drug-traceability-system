package com.moody.service;

import com.moody.gui.*;

import javax.swing.*;

public class FormManager {

    public static final String windowName = "Drug Traceability System";
    public static final BlockchainService blockchainService = new BlockchainServiceImpl();
    public static final AuthenticationService authenticationService = new AuthenticationServiceImpl();
    public static final LoginForm loginForm = new LoginForm(windowName, authenticationService);
    public static final RegisterForm registerForm = new RegisterForm(windowName, authenticationService);
    public static final SupplierForm supplierForm = new SupplierForm(windowName, blockchainService, authenticationService);
    public static final ManufacturerForm manufacturerForm = new ManufacturerForm(windowName, blockchainService, authenticationService);
    public static final DistributorForm distributorForm = new DistributorForm(windowName, blockchainService, authenticationService);
    public static final PharmacyForm phamarcyForm= new PharmacyForm(windowName, blockchainService, authenticationService);
    public static final TrackingForm trackingForm= new TrackingForm(windowName, blockchainService);

    public static void openForm(JFrame form){
        form.setVisible(true);
    }

    public static void closeForm(JFrame form){
        form.dispose();
    }

    public static void switchToTrackingDashboard(JFrame form){
        closeForm(form);
        openForm(trackingForm);
    }

}
