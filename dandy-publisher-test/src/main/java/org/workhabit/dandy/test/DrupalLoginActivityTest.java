package org.workhabit.dandy.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.workhabit.drupal.publisher.DrupalLoginActivity;
import com.workhabit.drupal.publisher.R;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/17/11, 12:45 AM
 */
public class DrupalLoginActivityTest extends ActivityInstrumentationTestCase2<DrupalLoginActivity>
{

    private Solo solo;

    public DrupalLoginActivityTest()
    {
        super("com.workhabit.drupal.publisher", DrupalLoginActivity.class);
    }

    public DrupalLoginActivityTest(Class<DrupalLoginActivity> activityClass)
    {
        super(activityClass);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testLogin() {
        solo.clickOnEditText(R.id.login_username);
        solo.clearEditText(R.id.login_username);
        solo.enterText(R.id.login_username, "testuser");

        solo.clickOnEditText(R.id.login_password);
        solo.clearEditText(R.id.login_password);
        solo.enterText(R.id.login_password, "testuser");

        solo.clickOnButton(R.id.login_button);
    }
}
