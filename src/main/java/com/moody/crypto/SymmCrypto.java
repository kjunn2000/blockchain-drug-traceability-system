package com.moody.crypto;

import com.moody.blockchain.TransactionRecord;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmCrypto {

    private static final String ALGORITHM = "AES";

    private Cipher cipher;

    public SymmCrypto() {
        try {
            this.cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt (String input, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decrypt(String cipherTxt, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = Base64.getDecoder().decode(cipherTxt);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }
}
