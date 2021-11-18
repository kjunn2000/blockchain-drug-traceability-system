package com.moody.service;

import com.moody.gui.*;

import javax.swing.*;

public class FormManager {

    public static final String windowName = "Drug Traceability System";
    public static final LoginForm loginForm = new LoginForm(windowName);
    public static final RegisterForm registerForm = new RegisterForm(windowName);
    public static final SupplierForm supplierForm = new SupplierForm(windowName);
    public static final ManufacturerForm manufacturerForm = new ManufacturerForm(windowName);
    public static final DistributorForm distributorForm = new DistributorForm(windowName);
    public static final PharmacyForm phamarcyForm= new PharmacyForm(windowName);
    public static final GuestForm guestForm = new GuestForm(windowName);

    public static void openForm(JFrame form){
        form.setVisible(true);
    }

    public static void closeForm(JFrame form){
        form.dispose();
    }

}
