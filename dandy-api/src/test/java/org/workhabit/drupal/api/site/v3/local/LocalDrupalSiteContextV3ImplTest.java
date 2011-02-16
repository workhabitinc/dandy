package org.workhabit.drupal.api.site.v3.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;
import org.workhabit.drupal.api.site.support.AndroidDrupalServicesRequestManagerImpl;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:40 PM
 */
public class LocalDrupalSiteContextV3ImplTest
{
    private DrupalSiteContextV3Impl context;
    private DrupalServicesRequestManager requestManager
            ;

    @Before
    public void setUp()
    {
        context = new DrupalSiteContextV3Impl("http://se.local", "dandy");
        requestManager = new AndroidDrupalServicesRequestManagerImpl();
        context.setRequestManager(requestManager);
    }

    /**
     * Fetches a test node against se.local with anonymous user.
     *
     * @throws DrupalFetchException
     */
    @Test
    public void testGetNode() throws DrupalFetchException
    {
        DrupalNode node = context.getNode(1);
        assertNotNull(node);
    }

    @Test
    public void testSaveNode() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser loggedInUser = authenticateTestUser();

            DrupalNode node = context.getNode(1);
            assertEquals(2, node.getUid());
            node.setNid(0);
            // resave the existing node for testing
            int nid = context.saveNode(node);
            assertFalse(nid == 0);
        } finally {
            context.logout();
        }
    }

    @Test
    public void testGetUser() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser loggedInUser = authenticateTestUser();
            DrupalUser user = context.getUser(2);
            assertNotNull(user);
            assertEquals(2, user.getUid());
        } finally {
            context.logout();
        }
    }

    @Test
    public void testUserLogin() throws DrupalLoginException, DrupalFetchException, DrupalLogoutException
    {
        DrupalUser loggedInUser = authenticateTestUser();
        DrupalUser currentUser = context.getCurrentUser();
        assertEquals(loggedInUser, currentUser);
        context.logout();
        assertNull(context.getCurrentUser());
    }

    private DrupalUser authenticateTestUser() throws DrupalLoginException, DrupalFetchException
    {
        DrupalUser loggedInUser = context.login("testuser", "testpass");
        assertNotNull(loggedInUser);
        return loggedInUser;
    }
}
