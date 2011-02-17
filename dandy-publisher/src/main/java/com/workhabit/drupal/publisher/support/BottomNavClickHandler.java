package com.workhabit.drupal.publisher.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.workhabit.drupal.publisher.DrupalHeadlineNodeListActivity;
import com.workhabit.drupal.publisher.DrupalTaxonomyListActivity;
import com.workhabit.drupal.publisher.R;


/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/12/10, 10:49 AM
 */
public class BottomNavClickHandler
{
    public static void bottomNavOnClick(View v, Activity a)
    {
        Intent intent = null;
        Context context = a.getApplicationContext();
        switch (v.getId()) {
            case R.id.button_headlines:
                intent = new Intent(context, DrupalHeadlineNodeListActivity.class);
                break;
            case R.id.button_categories:
                intent = new Intent(context, DrupalTaxonomyListActivity.class);
                break;
            case R.id.button_settings:
                break;
        }

        if (intent != null) {
            a.startActivity(intent);
        }

    }
}
