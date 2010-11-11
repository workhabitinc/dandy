package com.workhabit.drupal.publisher;

import android.app.ListActivity;
import android.content.Intent;
import android.view.View;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/3/10, 8:32 PM
 */
public abstract class AbstractAnDrupalListActivity extends ListActivity {
    public void bottomNavOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_headlines:
                intent = new Intent(getApplicationContext(), DrupalHeadlineNodeListActivity.class);
                intent.putExtra("viewName", "andrupal_recent");
                break;
            case R.id.button_categories:
                intent = new Intent(getApplicationContext(), DrupalTaxonomyListActivity.class);
                break;
            case R.id.button_readlater:
                intent = new Intent(getApplicationContext(), DrupalReadLaterActivity.class);
                break;
            case R.id.button_settings:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }

    }
}
