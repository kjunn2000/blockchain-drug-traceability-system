package com.moody.keygen;

import com.moody.crypto.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyAccess {

    /**
     * read the key
     */

    public static PublicKey getPublicKey() throws Exception{
        byte[] keyBytes = Files.readAllBytes( Paths.get( Configuration.PUBLICKEY_FILE ) );
        X509EncodedKeySpec spec = new X509EncodedKeySpec( keyBytes );
        return KeyFactory.getInstance( Configuration.PUBLICKEY_ALGORITHM ).generatePublic(spec);
    }

    public static PrivateKey getPrivateKey() throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get( Configuration.PRIVATEKEY_FILE ));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec( keyBytes );
        return KeyFactory.getInstance(Configuration.PUBLICKEY_ALGORITHM).generatePrivate(spec);
    }

}