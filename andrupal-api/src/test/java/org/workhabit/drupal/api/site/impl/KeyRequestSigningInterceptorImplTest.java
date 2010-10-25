package org.workhabit.drupal.api.site.impl;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 25, 2010, 10:20:03 AM
 */
public class KeyRequestSigningInterceptorImplTest {
    @Test
    public void testSign() throws Exception {
        KeyRequestSigningInterceptorImpl impl = new KeyRequestSigningInterceptorImpl();
        impl.setDrupalDomain("test.com");
        impl.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        Map<String, Object> data = new HashMap<String, Object>();
        impl.sign("http://test.com/services/json", "test", data);
        assertNotNull(data);
        assertTrue(data.containsKey("hash"));
        assertTrue(data.containsKey("nonce"));
        assertTrue(data.containsKey("timestamp"));
    }

    @Test
    public void testSignFailsWithInvalidKey() {
        KeyRequestSigningInterceptorImpl impl = new KeyRequestSigningInterceptorImpl();
        impl.setDrupalDomain("test.com");
        impl.setPrivateKey(null);

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            impl.sign("http://test.com/services/json", "test", data);
            fail("Should have thrown exception");
        } catch (NoSuchAlgorithmException e) {
            // OK
        } catch (InvalidKeyException e) {
            // OK
        }
    }
}
