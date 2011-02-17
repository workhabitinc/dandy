package com.workhabit.drupal.publisher;

import android.app.Application;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl;
import org.workhabit.drupal.api.site.support.AndroidDrupalServicesRequestManagerImpl;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 4:29:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DandyApplication extends Application
{
    private static String drupalSiteUrl;

    private static DrupalSiteContextV3Impl drupalSiteContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        drupalSiteUrl = this.getResources().getString(R.string.drupal_site_url);
    }

    /**
     * provides a helper method to return a singleton of a DrupalSiteContext for use by activities
     *
     * @return wired instance of DrupalSiteContext
     */
    public static DrupalSiteContext getDrupalSiteContext()
    {
        if (drupalSiteContext == null) {
            drupalSiteContext = new DrupalSiteContextV3Impl(drupalSiteUrl, "dandy");

            // set the android request manager on the context
            //
            AndroidDrupalServicesRequestManagerImpl requestManager = new AndroidDrupalServicesRequestManagerImpl();
            drupalSiteContext.setRequestManager(requestManager);
        }
        return drupalSiteContext;
    }
}
