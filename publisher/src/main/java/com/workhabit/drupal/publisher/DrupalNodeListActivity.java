package com.workhabit.drupal.publisher;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.inject.Inject;
import com.workhabit.drupal.R;
import com.workhabit.drupal.api.entity.DrupalNode;
import com.workhabit.drupal.api.site.DrupalFetchException;
import com.workhabit.drupal.api.site.impl.DrupalSiteContextImpl;
import roboguice.inject.InjectResource;

import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:01:14 PM
 */
public class DrupalNodeListActivity extends ListActivity {
    private DrupalNodeArrayAdapter nodeAdapter;
    @InjectResource(R.string.drupal_site_url)
    String drupalSiteUrl;
    @Inject
    private DrupalSiteContextImpl drupalSiteContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            // fetch list of recent drupal nodes
            //
            ArrayList<DrupalNode> nodes = (ArrayList<DrupalNode>) drupalSiteContext.getNodeView("andrupal_recent");

            // we use a custom node adapter
            if (nodeAdapter == null) {
                nodeAdapter = new DrupalNodeArrayAdapter(this, R.layout.row, nodes);
            }
            setListAdapter(nodeAdapter);
            nodeAdapter.notifyDataSetChanged();

        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
        setContentView(R.layout.nodelist);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getApplicationContext(), DrupalNodeActivity.class);
        intent.putExtra("nid", nodeAdapter.getNodes().get(position).getNid());
        this.startActivity(intent);
    }


}
