package com.moody.crypto;

import com.moody.keygen.KeyAccess;

import java.util.concurrent.TimeUnit;

public class TestASymmCrypto {

    public static void main(String[] args) throws Exception{

        ASymmCrypto crypto = new ASymmCrypto();

        String data = "hello brother";
        System.out.println( "Data: "+ data );

        String encrypted = crypto.encrypt(data, KeyAccess.getPublicKey());
        System.out.println( "Encrypted: "+encrypted );

        System.out.println();
        TimeUnit.SECONDS.sleep(3);

        String decrypted = crypto.decrypt(encrypted, KeyAccess.getPrivateKey());
        System.out.println( "Recovered: "+decrypted );

    }

}

