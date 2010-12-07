package org.workhabit.drupal.api.site.v3.site.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.CommonsHttpClientDrupalServicesRequestManager;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 12/7/10, 11:33 AM
 */
public class LocalDrupalSiteContextV3ImplTest {
    private DrupalSiteContextV3Impl context;

    @Before
    public void setUp() {
        DrupalServicesRequestManager requestManager = new CommonsHttpClientDrupalServicesRequestManager();
        context = new DrupalSiteContextV3Impl("http://ad3.hourglassone.com");
        context.setRequestManager(requestManager);
    }

    @Test
    public void testGetNode() throws DrupalFetchException {
        DrupalNode node = context.getNode(1);
        assertNotNull(node);
    }
}
