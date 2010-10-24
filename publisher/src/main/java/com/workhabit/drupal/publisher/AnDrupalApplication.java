package com.workhabit.drupal.publisher;

import com.google.inject.Module;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextImpl;
import org.workhabit.drupal.api.site.impl.KeyRequestSigningInterceptorImpl;
import com.workhabit.drupal.http.DrupalJsonRequestManager;
import roboguice.application.RoboApplication;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 4:29:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class AnDrupalApplication extends RoboApplication {
    private static String drupalSiteUrl;
    private static String privateKey;
    private static String drupalDomain;

    private static DrupalSiteContextImpl drupalSiteContext;

    @Override
    public void onCreate() {
        super.onCreate();
        drupalSiteUrl = this.getResources().getString(R.string.drupal_site_url);
        privateKey = this.getResources().getString(R.string.drupal_private_key);
        drupalDomain = this.getResources().getString(R.string.drupal_domain);
    }
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new DrupalConfigModule());
    }

    public static DrupalSiteContext getDrupalSiteContext() {
        if (drupalSiteContext == null) {
            drupalSiteContext = new DrupalSiteContextImpl(drupalSiteUrl);
            DrupalJsonRequestManager requestManager = new DrupalJsonRequestManager();
            KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
            requestSigningInterceptor.setDrupalDomain(drupalDomain);
            requestSigningInterceptor.setPrivateKey(privateKey);
            requestManager.setRequestSigningInterceptor(requestSigningInterceptor);
            drupalSiteContext.setJsonRequestManager(requestManager);
        }
        return drupalSiteContext;
    }
}
