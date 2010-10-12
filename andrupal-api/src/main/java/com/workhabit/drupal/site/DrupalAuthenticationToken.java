package com.workhabit.drupal.site;

import android.util.Log;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 4:42:08 PM
 */
public class DrupalAuthenticationToken {
    private String privateKey;
    /**
     * needed for signing
     */
    private final Charset asciiCs = Charset.forName("US-ASCII");
    private Mac apiKeyMac;

    public DrupalAuthenticationToken(String privateKey) {
        this.privateKey = privateKey;

        if (apiKeyMac == null) {
            SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(asciiCs
                    .encode(this.privateKey).array(), "HmacSHA256");
            try {
                apiKeyMac = Mac.getInstance("HmacSHA256");
                apiKeyMac.init(keySpec);
            } catch (NoSuchAlgorithmException e) {
                Log.e("crypto", e.getMessage(), e);
            } catch (InvalidKeyException e) {
                Log.e("crypto", e.getMessage(), e);
            }
        }
    }

    public String generateHmacHash(Long timestamp, String serviceDomain, String nonce, String operation) throws Exception {
        String hashString = String.format("%s;%s;%s;%s", Long.toString(timestamp), serviceDomain, nonce, operation);
        byte[] hash = apiKeyMac.doFinal(asciiCs.encode(hashString).array());

        String result = "";
        for (byte aHash : hash) {
            // FYI: I don't understand why this is here..
            result += Integer.toString((aHash & 0xff) + 0x100, 16)
                    .substring(1);
        }
        return result;
    }
}
