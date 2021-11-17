package com.moody.keygen;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;

public class SecretCharsKeyGen {

    private static final String ALGORITHM = "AES";

    private static final String SECRET_CHARS = "thisissymmetriccryptosystemdem";

    public static Key keygen(){
        return new SecretKeySpec(
                Arrays.copyOf(SECRET_CHARS.getBytes(), 16),
                ALGORITHM
        );
    }
}
