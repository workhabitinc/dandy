package org.workhabit.drupal.api.site.v3.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.CommonsHttpClientDrupalServicesRequestManager;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:40 PM
 */
public class LocalDrupalSiteContextV3ImplTest
{
    private DrupalSiteContextV3Impl context;

    @Before
    public void setUp() {
        context = new DrupalSiteContextV3Impl("http://se.local", "dandy");
        CommonsHttpClientDrupalServicesRequestManager requestManager = new CommonsHttpClientDrupalServicesRequestManager();
        context.setRequestManager(requestManager);
    }
    @Test
    public void testGetNode() throws DrupalFetchException
    {
        DrupalNode node = context.getNode(1);
        assertNotNull(node);
    }

    @Test
    public void testSaveNode() throws DrupalFetchException
    {
        DrupalNode node = context.getNode(1);
        node.setNid(0);
        // resave the existing node for testing
        int nid = context.saveNode(node);
        assertFalse(nid == 0);
    }

    @Test
    public void testUserLogin() throws DrupalLoginException, DrupalFetchException, DrupalLogoutException
    {
        DrupalUser user = context.login("testuser", "testpass");
        assertNotNull(user);
        DrupalUser currentUser = context.getCurrentUser();
        assertEquals(user, currentUser);
        context.logout();
        assertNull(context.getCurrentUser());
    }
}
