package com.workhabit.drupal.publisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.workhabit.drupal.publisher.support.BottomNavClickHandler;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:19:12 PM
 */
public abstract class AbstractDandyActivity extends Activity {
    public void bottomNavOnClick(View v) {
        BottomNavClickHandler.bottomNavOnClick(v, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        DandyApplication.saveInstanceState(outState);
    }
}
