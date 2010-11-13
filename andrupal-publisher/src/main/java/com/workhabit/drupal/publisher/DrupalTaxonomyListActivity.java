package com.workhabit.drupal.publisher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import com.workhabit.drupal.publisher.support.DrupalTaxonomyAdapter;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.site.DrupalFetchException;
import org.workhabit.drupal.api.site.DrupalSiteContext;

import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:55:47 PM
 */
public class DrupalTaxonomyListActivity extends AbstractAnDrupalListActivity {
    private DrupalTaxonomyAdapter drupalTaxonomyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrupalSiteContext drupalSiteContext = AnDrupalApplication.getDrupalSiteContext();
        try {
            ArrayList<DrupalTaxonomyTerm> terms = (ArrayList<DrupalTaxonomyTerm>) drupalSiteContext.getCategoryList();
            drupalTaxonomyAdapter = new DrupalTaxonomyAdapter(this, R.layout.row, terms);
            this.setListAdapter(drupalTaxonomyAdapter);
            drupalTaxonomyAdapter.notifyDataSetChanged();
        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
        setContentView(R.layout.taxonomylist);
        // set active state on button
        Button b = (Button) findViewById(R.id.button_categories);
        b.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(R.drawable.button_categories_icon_active),
                null,
                null
        );
        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_active_bg));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this.getApplicationContext(), DrupalNodeListViewActivity.class);
        intent.putExtra("viewName", "andrupal_category_nodes");
        intent.putExtra("viewArguments", Integer.toString(drupalTaxonomyAdapter.getTerms().get(position).getTid()));
        this.startActivity(intent);
    }
}
