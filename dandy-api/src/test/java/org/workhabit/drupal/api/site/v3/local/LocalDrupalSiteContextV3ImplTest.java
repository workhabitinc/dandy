package org.workhabit.drupal.api.site.v3.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;
import org.workhabit.drupal.api.site.support.AndroidDrupalServicesRequestManagerImpl;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:40 PM
 */
public class LocalDrupalSiteContextV3ImplTest
{
    private DrupalSiteContextV3Impl context;

    @Before
    public void setUp()
    {
        context = new DrupalSiteContextV3Impl("http://se.local", "dandy");
        DrupalServicesRequestManager requestManager = new AndroidDrupalServicesRequestManagerImpl();
        context.setRequestManager(requestManager);
    }

    /**
     * Fetches a test node against se.local with anonymous user.
     */
    @SuppressWarnings({"JavaDoc"})
    @Test
    public void testGetNode() throws DrupalFetchException
    {
        DrupalNode node = context.getNode(1);
        assertNotNull(node);
    }

    @Test
    public void testSaveNewNode() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser loggedInUser = authenticateTestUser();
            assertNotNull(loggedInUser);
            DrupalNode node = context.getNode(1);
            assertEquals(2, node.getUid());
            node.setNid(0);
            // re-save the existing node for testing
            int nid = context.saveNode(node);
            assertFalse(nid == 0);
        } finally {
            context.logout();
        }
    }

    @Test
    public void testSaveExistingNode() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser loggedInUser = authenticateTestUser();
            assertNotNull(loggedInUser);
            DrupalNode node = context.getNode(1);
            assertEquals(2, node.getUid());
            String testTitle = TestData.getTestTitle();
            node.setTitle(testTitle);
            // re-save the existing node for testing
            int nid = context.saveNode(node);
            assertEquals(1, nid);
            node = context.getNode(1);
            assertNotNull(node);
            assertEquals(testTitle, node.getTitle());
        } finally {
            context.logout();
        }
    }

    @Test
    public void testGetUser() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser loggedInUser = authenticateTestUser();
            assertNotNull(loggedInUser);
            DrupalUser user = context.getUser(2);
            assertNotNull(user);
            assertEquals(2, user.getUid());
        } finally {
            context.logout();
        }
    }

    @Test
    public void testUserLoginLogout() throws DrupalLoginException, DrupalFetchException, DrupalLogoutException
    {
        DrupalUser loggedInUser = authenticateTestUser();
        DrupalUser currentUser = context.getCurrentUser();
        assertEquals(loggedInUser, currentUser);
        context.logout();
        assertNull(context.getCurrentUser());
    }


    @Test
    public void testGetCommentsForNode() throws DrupalFetchException, DrupalLoginException
    {
        DrupalUser drupalUser = authenticateTestUser();
        assertNotNull(drupalUser);
        List<DrupalComment> comments = context.getComments(2);
        assertNotNull(comments);


    }

    @Test
    public void testInvalidParameterThrowsError()
    {
        try {
            context.login("", "");
            fail("Should have thrown exception");
        } catch (DrupalLoginException e) {
            assertNotNull(e.getMessage());
            assertEquals("401: Unauthorized: Wrong username or password.", e.getMessage());
        } catch (DrupalFetchException e) {
            assertNotNull(e.getMessage());
        }


    }

    @Test
    public void testGetNodeView() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        try {
            DrupalUser user = authenticateTestUser();
            assertNotNull(user);
            List<DrupalNode> list = context.getNodeView("dandy_recent");
            assertNotNull(list);
            assertFalse(list.size() == 0);

            // test with offset = 0 and limit == 1
            List<DrupalNode> shortlist = context.getNodeView("dandy_recent", null, 0, 1);
            assertNotNull(shortlist);
            assertEquals(1, shortlist.size());

            // now check offset = 1 and limit =1 and compare to second record in all list above.
            List<DrupalNode> secondlist = context.getNodeView("dandy_recent", null, 1, 1);
            assertNotNull(secondlist);
            assertEquals(1, shortlist.size());
            DrupalNode second = secondlist.get(0);
            assertEquals(second.getNid(), list.get(1).getNid());

            // save a node, verify that it shows up at the top of the view
            //
            second.setNid(0);
            String testTitle = TestData.getTestTitle();
            second.setTitle(testTitle);
            int nid = context.saveNode(second);

            List<DrupalNode> thirdlist = context.getNodeView("dandy_recent", null, 0, 1);
            assertNotNull(thirdlist);
            assertEquals(testTitle, thirdlist.get(0).getTitle());
            assertEquals(nid, thirdlist.get(0).getNid());
        } finally {
            context.logout();
        }
    }

    @Test
    public void testRestoreInstanceState() throws DrupalFetchException, DrupalLoginException, DrupalLogoutException
    {
        DrupalSiteContextV3Impl context1 = new DrupalSiteContextV3Impl("http://se.local", "dandy");
        try {
            AndroidDrupalServicesRequestManagerImpl requestManager1 = new AndroidDrupalServicesRequestManagerImpl();
            context1.setRequestManager(requestManager1);
            context1.login("testuser", "testpass");

            DrupalSiteContextInstanceState savedState = context1.getSavedState();

            context1 = new DrupalSiteContextV3Impl("http://se.local", "dandy");
            requestManager1 = new AndroidDrupalServicesRequestManagerImpl();
            context1.setRequestManager(requestManager1);
            context1.initializeSavedState(savedState);

            DrupalNode node = context1.getNode(1);
            node.setNid(0);
            int nid = context1.saveNode(node);
            assertFalse(nid == 0);
        } finally {
            context1.logout();
        }

    }

    private DrupalUser authenticateTestUser() throws DrupalLoginException, DrupalFetchException
    {
        DrupalUser loggedInUser = context.login("testuser", "testpass");
        assertNotNull(loggedInUser);
        return loggedInUser;
    }
}
