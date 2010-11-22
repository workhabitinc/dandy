package org.workhabit.drupal.api.site.impl;

import org.workhabit.drupal.api.site.NonceFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/17/10, 3:23 PM
 */
public class NonceFactoryImpl implements NonceFactory {
    public String getNonce() {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(Double.toString(System.currentTimeMillis() + Math.random()).getBytes());
            StringBuffer hexString = new StringBuffer();
            byte messageDigest[] = digest.digest();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // not happening.
        }
        return null;
    }
}
