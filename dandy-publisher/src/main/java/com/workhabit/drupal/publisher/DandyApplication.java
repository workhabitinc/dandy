package com.workhabit.drupal.publisher;

import android.app.Application;
import android.os.Bundle;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
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
    private static final String DRUPAL_SITE_CONTEXT_INSTANCE_STATE = "drupalSiteContextInstanceState";

    @Override
    public void onCreate()
    {
        super.onCreate();
        drupalSiteUrl = this.getResources().getString(R.string.drupal_site_url);
    }

    /**
     * provides a helper method to return a singleton of a DrupalSiteContext for use by activities
     *
     * @param savedInstanceState
     * @return wired instance of DrupalSiteContext
     */
    public static DrupalSiteContext getDrupalSiteContext(Bundle savedInstanceState)
    {
        if (drupalSiteContext == null) {
            drupalSiteContext = new DrupalSiteContextV3Impl(drupalSiteUrl, "dandy");
            if (savedInstanceState != null && savedInstanceState.containsKey(DRUPAL_SITE_CONTEXT_INSTANCE_STATE)) {
                DrupalSiteContextInstanceState instanceState = (DrupalSiteContextInstanceState)savedInstanceState.getSerializable(DRUPAL_SITE_CONTEXT_INSTANCE_STATE);
                if (instanceState != null) {
                    drupalSiteContext.initializeSavedState(instanceState);
                }
            }
        }
        // set the android request manager on the context
        //
        AndroidDrupalServicesRequestManagerImpl requestManager = new AndroidDrupalServicesRequestManagerImpl();
        drupalSiteContext.setRequestManager(requestManager);

        return drupalSiteContext;
    }


    public static void saveInstanceState(Bundle outState)
    {
        if (drupalSiteContext != null) {
            outState.putSerializable(DRUPAL_SITE_CONTEXT_INSTANCE_STATE, drupalSiteContext.getSavedState());
        }
    }
}
