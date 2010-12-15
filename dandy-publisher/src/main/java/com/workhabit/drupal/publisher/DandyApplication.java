package com.workhabit.drupal.publisher;

import android.app.Application;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl;
import org.workhabit.drupal.api.site.impl.v2.KeyRequestSigningInterceptorImpl;
import org.workhabit.drupal.http.AndroidDrupalServicesRequestManagerImpl;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 4:29:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DandyApplication extends Application {
    private static String drupalSiteUrl;
    private static String privateKey;
    private static String drupalDomain;

    private static DrupalSiteContextV2Impl drupalSiteContext;

    @Override
    public void onCreate() {
        super.onCreate();
        drupalSiteUrl = this.getResources().getString(R.string.drupal_site_url);
        privateKey = this.getResources().getString(R.string.drupal_private_key);
        drupalDomain = this.getResources().getString(R.string.drupal_domain);
    }

    /**
     * provides a helper method to return a singleton of a DrupalSiteContext for use by activities
     *
     * @return wired instance of DrupalSiteContext
     */
    public static DrupalSiteContext getDrupalSiteContext() {
        if (drupalSiteContext == null) {
            drupalSiteContext = new DrupalSiteContextV2Impl(drupalSiteUrl);
            AndroidDrupalServicesRequestManagerImpl requestManager = new AndroidDrupalServicesRequestManagerImpl();

            // set the request interceptor to handle signing based on drupal's key authentication
            //
            KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
            requestSigningInterceptor.setDrupalDomain(drupalDomain);
            requestSigningInterceptor.setPrivateKey(privateKey);
            requestManager.setRequestSigningInterceptor(requestSigningInterceptor);

            // set the android request manager on the context
            //
            drupalSiteContext.setDrupalServicesRequestManager(requestManager);
        }
        return drupalSiteContext;
    }
}
