package com.workhabit.drupal.publisher;

import android.os.Bundle;
import android.widget.Button;

import javax.naming.ldap.Rdn;

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
        Button b = (Button) findViewById(R.id.button_headlines);
        b.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.button_headlines_icon_active), null, null);
        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_active_bg));
    }
}
