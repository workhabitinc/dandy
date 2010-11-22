package org.workhabit.drupal.api.site.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.site.CalendarFactory;
import org.workhabit.drupal.api.site.NonceFactory;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 25, 2010, 10:20:03 AM
 */
public class KeyRequestSigningInterceptorImplTest {
    private CalendarFactory mockCalendarFactory;
    private NonceFactory mockNonceFactory;
    private Mockery mockery;

    @Before
    public void setUp() {
        mockery = new Mockery();
        mockCalendarFactory = mockery.mock(CalendarFactory.class);
        mockNonceFactory = mockery.mock(NonceFactory.class);
    }

    @Test
    public void testSign() throws Exception {
        KeyRequestSigningInterceptorImpl impl = new KeyRequestSigningInterceptorImpl();
        impl.setDrupalDomain("workhabit.com");
        impl.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        Map<String, Object> data = new HashMap<String, Object>();
        impl.setCalendarFactory(mockCalendarFactory);
        impl.setNonceFactory(mockNonceFactory);
        final Date expectedDate = new Date(1290036075000L);
        mockery.checking(new Expectations() {
            {
                one(mockCalendarFactory).getInstance();
                will(returnValue(expectedDate));
                one(mockNonceFactory).getNonce();
                will(returnValue("d3b07384d113edec49eaa6238ad5ff00"));
            }
        });

        impl.sign("http://test.com/services/json", "test", data);
        assertNotNull(data);
        assertTrue(data.containsKey("hash"));
        assertEquals("fd5e8cbf538ff917c744e7b5860c7d997095d3942dbf38d917f825e2fcaf8a05", data.get("hash"));
        assertTrue(data.containsKey("nonce"));
        assertTrue(data.containsKey("domain_name"));
        assertTrue(data.containsKey("domain_time_stamp"));
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
