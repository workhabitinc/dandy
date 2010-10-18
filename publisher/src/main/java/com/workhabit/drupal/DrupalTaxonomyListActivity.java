package com.workhabit.drupal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.inject.Inject;
import com.workhabit.drupal.entity.DrupalNode;
import com.workhabit.drupal.entity.DrupalTaxonomyTerm;
import com.workhabit.drupal.site.DrupalFetchException;
import com.workhabit.drupal.site.DrupalSiteContext;

import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:55:47 PM
 */
public class DrupalTaxonomyListActivity extends Activity {
    @Inject
    private DrupalSiteContext drupalSiteContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ArrayList<DrupalTaxonomyTerm> terms = (ArrayList<DrupalTaxonomyTerm>) drupalSiteContext.getTermView("andrupal_categories");
        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }

    }
}
