package com.moody;

import com.moody.authentication.UserBank;
import com.moody.blockchain.Blockchain;
import com.moody.service.FormManager;

public class Main {

    public static void main(String[] args) throws Exception {
//        Uncomment if userbank.txt has data
        UserBank.loadUserData();

//        The configure() should be done once at the start of the blockchain
//        Blockchain.configure();

//        After first time can directly load master file
        Blockchain.loadMasterFile();

        FormManager.openForm(FormManager.loginForm);
    }
}
