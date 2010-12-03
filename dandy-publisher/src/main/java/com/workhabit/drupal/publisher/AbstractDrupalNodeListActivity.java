package com.workhabit.drupal.publisher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import com.workhabit.drupal.publisher.support.DrupalNodeArrayAdapter;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.DrupalSiteContext;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/3/10, 8:45 PM
 */
public abstract class AbstractDrupalNodeListActivity extends AbstractDandyListActivity {
    protected DrupalNodeArrayAdapter nodeAdapter;
    protected DrupalSiteContext drupalSiteContext;
    protected String viewArguments;
    protected String viewName;

    protected void setViewName(String viewName) {
        this.viewName = viewName;
    }

    protected void setViewArguments(String viewArguments) {
        this.viewArguments = viewArguments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drupalSiteContext = DandyApplication.getDrupalSiteContext();

        try {
            if (viewName == null || "".equals(viewName)) {
                viewName = getIntent().getStringExtra("viewName");
            }
            if (viewArguments == null) {
                viewArguments = getIntent().getStringExtra("viewArguments");
            }
            // fetch list of recent drupal nodes
            //
            List<DrupalNode> nodes = doGetNodes(viewArguments, this.viewName);

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

    protected abstract List<DrupalNode> doGetNodes(String viewArguments, String viewName) throws DrupalFetchException;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getApplicationContext(), DrupalNodeActivity.class);
        intent.putExtra("nid", nodeAdapter.getNodes().get(position).getNid());
        this.startActivity(intent);
    }
}
