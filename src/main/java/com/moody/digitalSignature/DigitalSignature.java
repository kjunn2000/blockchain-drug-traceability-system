package com.moody.digitalSignature;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public interface DigitalSignature {

    String sign( String data, PrivateKey privateKey);

    boolean verify( String data, String signatureString, PublicKey publicKey);
}
