package com.moody.blockchain;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class Hasher {

    public static String hash( String data, String algorithm )
    {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update( data.getBytes() );
            byte[] hashBytes = md.digest();
            hash = String.valueOf( Hex.encodeHex(hashBytes) );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String hash( String data, String salt, String algorithm ) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update( data.getBytes() );
            md.update( salt.getBytes() );
            byte[] hashBytes = md.digest();
            hash = String.valueOf( Hex.encodeHex(hashBytes) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String hash( byte[] blockBytes, String algorithm ) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest( blockBytes );
            hash = String.valueOf( Hex.encodeHex(hashBytes) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

}