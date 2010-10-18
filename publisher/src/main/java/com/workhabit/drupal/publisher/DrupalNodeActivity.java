package com.workhabit.drupal.publisher;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.google.inject.Inject;
import com.workhabit.drupal.R;
import com.workhabit.drupal.api.entity.DrupalNode;
import com.workhabit.drupal.api.site.DrupalFetchException;
import com.workhabit.drupal.api.site.impl.DrupalSiteContextImpl;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 7:39:37 PM
 */
public class DrupalNodeActivity extends Activity {
    @InjectView(R.id.nodeTitle)
    TextView titleView;
    @InjectView(R.id.nodeBody)
    TextView bodyView;
    @InjectResource(R.string.drupal_site_url)
    String drupalSiteUrl;

    @Inject
    private DrupalSiteContextImpl siteContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int nid = getIntent().getExtras().getInt("nid");

        setContentView(R.layout.node);
        try {
            DrupalNode node = siteContext.getNode(nid);
            titleView.setText(node.getTitle());
            bodyView.setText(Html.fromHtml("<p>" + node.getBody().replaceAll("\r\n", "\n").replaceAll("\n\n", "</p><p>") + "</p>"));
        } catch (DrupalFetchException e) {
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }

    }
}
