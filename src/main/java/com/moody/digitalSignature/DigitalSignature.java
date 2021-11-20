package com.moody.digitalSignature;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface DigitalSignature {

    String sign( String data, PrivateKey privateKey);

    boolean verify( String data, String signatureString, PublicKey publicKey);
}
