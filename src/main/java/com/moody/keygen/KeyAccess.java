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

    public static PublicKey getPublicKey(String filename) throws Exception{
        byte[] keyBytes = Files.readAllBytes( Paths.get( Configuration.KEY_FILE+filename+"/"+filename+"_pubkey") );
        X509EncodedKeySpec spec = new X509EncodedKeySpec( keyBytes );
        return KeyFactory.getInstance( Configuration.PUBLICKEY_ALGORITHM ).generatePublic(spec);
    }

    public static PrivateKey getPrivateKey(String filename) throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get( Configuration.KEY_FILE+filename+"/"+filename+"_prikey"));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec( keyBytes );
        return KeyFactory.getInstance(Configuration.PUBLICKEY_ALGORITHM).generatePrivate(spec);
    }

}