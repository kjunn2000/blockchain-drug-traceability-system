package com.moody;

import com.moody.authentication.UserBank;
import com.moody.blockchain.Blockchain;
import com.moody.gui.SupplierForm;
import com.moody.service.FormManager;

public class Main {


    public static void main(String[] args) throws Exception {
        UserBank.loadUserData();
//        Blockchain.configure();
        Blockchain.loadMasterFile();

        FormManager.openForm(FormManager.loginForm);
    }
}
