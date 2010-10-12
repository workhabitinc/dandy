package com.workhabit.drupal;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.workhabit.drupal.NodeListActivity;
import com.workhabit.drupal.R;
import roboguice.activity.RoboActivity;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:19:12 PM
 */
public abstract class AbstractAnDrupalActivity extends RoboActivity {

    /**
     * load menu from xml
     *
     * @param menu the menu that invoked the create operation
     * @return true if create succeeded
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                this.handleRefresh();
                break;
            case R.id.recent:
                this.startActivity(new Intent(this.getApplicationContext(), NodeListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    protected abstract void handleRefresh();
}
