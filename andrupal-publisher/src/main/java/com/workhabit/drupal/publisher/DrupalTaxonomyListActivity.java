package com.workhabit.drupal.publisher;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.site.DrupalFetchException;
import org.workhabit.drupal.api.site.DrupalSiteContext;

import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:55:47 PM
 */
public class DrupalTaxonomyListActivity extends ListActivity {
    private DrupalTaxonomyAdapter drupalTaxonomyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrupalSiteContext drupalSiteContext = AnDrupalApplication.getDrupalSiteContext();
        try {
            ArrayList<DrupalTaxonomyTerm> terms = (ArrayList<DrupalTaxonomyTerm>) drupalSiteContext.getCategoryList();
            drupalTaxonomyAdapter = new DrupalTaxonomyAdapter(this, R.layout.row, terms);
            this.setListAdapter(drupalTaxonomyAdapter);

        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this.getApplicationContext(), DrupalNodeListActivity.class);
        intent.putExtra("viewName", "andrupal_category_nodes");
        intent.putExtra("viewArguments", Integer.toString(drupalTaxonomyAdapter.getTerms().get(position).getTid()));
        this.startActivity(intent);
    }
}
