package com.workhabit.drupal.site.impl;

import com.workhabit.drupal.site.DrupalAuthenticationToken;
import com.workhabit.drupal.site.RequestSigningInterceptor;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 14, 2010, 12:07:13 PM
 */
public class KeyRequestSigningInterceptorImpl implements RequestSigningInterceptor {

    DrupalAuthenticationToken token;
    private String drupalDomain;

    public void setToken(DrupalAuthenticationToken token) {
        this.token = token;
    }

    public void setDrupalDomain(String drupalDomain) {
        this.drupalDomain = drupalDomain;
    }

    public void sign(String path, String method, Map<String, Object> data) throws Exception {
        Long timestamp = Calendar.getInstance().getTimeInMillis();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(Double.toString(System.currentTimeMillis() + Math.random()).getBytes());
        StringBuffer hexString = new StringBuffer();
        byte messageDigest[] = digest.digest();
        for (byte aMessageDigest : messageDigest) {
            hexString.append(Integer.toHexString(0xFF & aMessageDigest));
        }

        String nonce = hexString.toString();
        //();
        String hash = token.generateHmacHash(timestamp, drupalDomain, nonce, method);

        // add params for hash
        data.put("hash", hash);
        data.put("timestamp", Long.toString(timestamp));
        data.put("nonce", nonce);
    }

}
