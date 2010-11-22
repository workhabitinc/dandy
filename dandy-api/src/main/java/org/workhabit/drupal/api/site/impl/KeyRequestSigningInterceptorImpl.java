package org.workhabit.drupal.api.site.impl;

import org.workhabit.drupal.api.site.CalendarFactory;
import org.workhabit.drupal.api.site.DefaultCalendarFactory;
import org.workhabit.drupal.api.site.NonceFactory;
import org.workhabit.drupal.api.site.RequestSigningInterceptor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 14, 2010, 12:07:13 PM
 */
public class KeyRequestSigningInterceptorImpl implements RequestSigningInterceptor {
    private String drupalDomain;
    private String privateKey;
    private Mac apiKeyMac;
    private final Charset asciiCs = Charset.forName("US-ASCII");
    private CalendarFactory calendarFactory;
    private NonceFactory nonceFactory;

    public KeyRequestSigningInterceptorImpl() {
        calendarFactory = new DefaultCalendarFactory();
        nonceFactory = new NonceFactoryImpl();
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setDrupalDomain(String drupalDomain) {
        this.drupalDomain = drupalDomain;
    }

    public void setCalendarFactory(CalendarFactory calendarFactory) {
        this.calendarFactory = calendarFactory;
    }

    public void setNonceFactory(NonceFactory nonceFactory) {
        this.nonceFactory = nonceFactory;
    }

    public void sign(String path, String method, Map<String, Object> data) throws NoSuchAlgorithmException, InvalidKeyException {
        if (apiKeyMac == null) {
            if (privateKey == null) {
                throw new InvalidKeyException();
            }
            // Create the HMAC/SHA256 key
            final SecretKeySpec signingKey = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");

            // Create the message authentication code (MAC)
            apiKeyMac = Mac.getInstance("HmacSHA256");
            apiKeyMac.init(signingKey);
        }

        Long timestamp = calendarFactory.getInstance().getTime() / 1000;
        String nonce = nonceFactory.getNonce();

        String hash = generateHmacHash(timestamp, drupalDomain, nonce, method);

        // add params for hash
        data.put("domain_name", drupalDomain);
        data.put("hash", hash);
        data.put("domain_time_stamp", Long.toString(timestamp));
        data.put("nonce", nonce);
    }

    private String generateHmacHash(Long timestamp, String serviceDomain, String nonce, String method) {
        String hashString = String.format("%s;%s;%s;%s", Long.toString(timestamp), serviceDomain, nonce, method);
        StringBuffer result = new StringBuffer();

        // Compute the HMAC value
        try {
            byte[] hash = apiKeyMac.doFinal(hashString.getBytes("UTF-8"));

            for (byte aHash : hash) {
                result.append(Integer.toString((aHash & 0xff) + 0x100, 16).substring(1));
            }
        } catch (UnsupportedEncodingException e) {
            // can someone explain to me why exactly this isn't a RuntimeException? FAIL SAUCE.
        }
        return result.toString();
    }

}
