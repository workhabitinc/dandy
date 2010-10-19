package com.workhabit.drupal.publisher;

import com.google.inject.Module;
import com.workhabit.drupal.api.site.DrupalSiteContext;
import com.workhabit.drupal.api.site.impl.DrupalSiteContextImpl;
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

    @Override
    public void onCreate() {
        super.onCreate();
        drupalSiteUrl = this.getResources().getString(R.string.drupal_site_url);
        privateKey = this.getResources().getString(R.string.drupal_private_key);
    }

    private static DrupalSiteContext drupalSiteContext;
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new DrupalConfigModule());
    }

    public static DrupalSiteContext getDrupalSiteContext() {
        if (drupalSiteContext == null) {
            drupalSiteContext = new DrupalSiteContextImpl(drupalSiteUrl, privateKey);
        }
        return drupalSiteContext;
    }
}
