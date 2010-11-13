package org.workhabit.drupal.api.site.local;

import org.junit.Before;
import org.junit.Test;
import org.workhabit.drupal.api.CommonsHttpClientJsonRequestManager;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.site.DrupalFetchException;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextImpl;
import org.workhabit.drupal.api.site.impl.KeyRequestSigningInterceptorImpl;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/12/10, 10:56 AM
 */
public class LocalDrupalSiteContextImplTest {
    private DrupalSiteContextImpl drupalSiteContext;

    @Before
    public void setUp() {
        CommonsHttpClientJsonRequestManager manager = new CommonsHttpClientJsonRequestManager();
        KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
        requestSigningInterceptor.setDrupalDomain("workhabit.com");
        requestSigningInterceptor.setPrivateKey("9e47c52fae3c36baff404f7072e46547");
        manager.setRequestSigningInterceptor(requestSigningInterceptor);

        drupalSiteContext = new DrupalSiteContextImpl("http://ad.hourglassone.com");
        drupalSiteContext.setJsonRequestManager(manager);
    }

    @Test
    public void testSaveComment() throws DrupalFetchException {
        DrupalComment comment = new DrupalComment();
        comment.setNid(1);
        comment.setComment("test body 1");
        comment.setSubject("Test title");
        comment.setUid(0);
        //comment.setUid(1);
        drupalSiteContext.saveComment(comment);
    }
}
