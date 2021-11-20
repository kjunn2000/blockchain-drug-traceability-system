package com.moody.crypto;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class ASymmCrypto {

    private Cipher cipher;

    public ASymmCrypto() {
        try {
            cipher = Cipher.getInstance( Configuration.PUBLICKEY_ALGORITHM );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt( String input, PublicKey key ) throws Exception{
        String cipherText = null;
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //encrypt
        byte[] cipherBytes = cipher.doFinal( input.getBytes() );
        cipherText = Base64.getEncoder().encodeToString(cipherBytes);
        return cipherText;
    }

    public String decrypt( String cipherText, PrivateKey key ) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE, key);
        cipher.update( Base64.getDecoder().decode( cipherText.getBytes() ) );
        byte[] dataBytes = cipher.doFinal();
        return new String(dataBytes);
    }

}

