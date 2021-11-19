package com.moody.digitalSignature;

import java.security.*;
import java.util.Base64;

public class DigitalSignatureImpl implements DigitalSignature{

    private Signature signature;

    private final String CRYPTO_ALGORITHM = "RSA";

    public DigitalSignatureImpl() {
        try {
            signature = Signature.getInstance( "SHA256WithRSA" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sign( String data, PrivateKey privateKey) {
        try {
            signature.initSign( privateKey );
            signature.update( data.getBytes() );
            return Base64.getEncoder().encodeToString( signature.sign() );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verify( String data, String signatureString, PublicKey publicKey) {
        try {
            signature.initVerify(publicKey);
            signature.update( data.getBytes() );
            return signature.verify( Base64.getDecoder().decode(signatureString) );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}