package com.workhabit.drupal.publisher;

import android.os.Bundle;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/11/10, 11:31 AM
 */
public class DrupalHeadlineNodeListActivity extends DrupalNodeListViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewName = "andrupal_recent";
        viewArguments = "";
        super.onCreate(savedInstanceState);
    }
}
