package com.moody.crypto;


import com.moody.blockchain.TransactionRecord;

import javax.crypto.Cipher;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class ASymmCrypto {

    //Q: Whats the API needed to support en/de-cryption?
    //A: Cipher
    private Cipher cipher;

    public ASymmCrypto() {
        try {
            cipher = Cipher.getInstance( Configuration.PUBLICKEY_ALGORITHM );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * encrypt(String, PublicKey)
     */
    public String encrypt( String input, PublicKey key ) throws Exception{
        String cipherText = null;
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //encrypt
        byte[] cipherBytes = cipher.doFinal( input.getBytes() );
        cipherText = Base64.getEncoder().encodeToString(cipherBytes);
        return cipherText;
    }


    /**
     * decrypt(String, PrivateKey)
     */
    public String decrypt( String cipherText, PrivateKey key ) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE, key);
        cipher.update( Base64.getDecoder().decode( cipherText.getBytes() ) );
        byte[] dataBytes = cipher.doFinal();
        return new String(dataBytes);
    }

}

