package com.moody.service;

import com.moody.authentication.User;
import com.moody.authentication.UserBank;
import com.moody.crypto.SymmCrypto;
import com.moody.keygen.KeyPairMaker;
import com.moody.keygen.SecretCharsKeyGen;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.util.Objects;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean logIn(String fullName, String password) {
        User user = UserBank.findUser(fullName, password);
        if (!Objects.nonNull(user)){
           return false;
        }
        UserBank.setCurrentUser(user);
        FormManager.closeForm(FormManager.loginForm);
        switch (user.getBusinessType()){
            case SUPPLIER:{
                FormManager.openForm(FormManager.supplierForm);
                break;
            }
            case MANUFACTURER:{
                FormManager.openForm(FormManager.manufacturerForm);
                break;
            }
            case DISTRIBUTOR:{
                FormManager.openForm(FormManager.distributorForm);
                break;
            }
            case PHARMACY:{
                FormManager.openForm(FormManager.phamarcyForm);
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }

    @Override
    public void logOut(JFrame form) {
        UserBank.setCurrentUser(null);
        FormManager.closeForm(form);
        FormManager.openForm(FormManager.loginForm);
    }

    @Override
    public boolean register(User user) {
        SymmCrypto symmCrypto = new SymmCrypto();
        String encryptFullName = "";
        try {
            encryptFullName = symmCrypto.encrypt(user.getFullName(), SecretCharsKeyGen.keygen());
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        user.setEncryptFullName(encryptFullName);
        encryptFullName = encryptFullName.replaceAll("/","");
        KeyPairMaker.createWithFileName(encryptFullName);
        UserBank.addUser(user);
        FormManager.closeForm(FormManager.registerForm);
        FormManager.openForm(FormManager.loginForm);
        return true;
    }
}
