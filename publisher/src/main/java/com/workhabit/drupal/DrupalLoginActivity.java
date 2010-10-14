package com.workhabit.drupal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.workhabit.drupal.entity.DrupalUser;
import com.workhabit.drupal.site.DrupalFetchException;
import com.workhabit.drupal.site.DrupalLoginException;
import com.workhabit.drupal.site.DrupalSiteContext;
import com.workhabit.drupal.site.impl.DrupalSiteContextImpl;
import roboguice.inject.InjectResource;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:01:59 PM
 */
public class DrupalLoginActivity extends AbstractAnDrupalActivity {
    @SuppressWarnings({"UnusedDeclaration"})
    @InjectResource(R.string.drupal_site_url)
    private String drupalSiteUrl;
    @SuppressWarnings({"UnusedDeclaration"})
    @InjectResource(R.string.drupal_private_key)
    private String privateKey;

    private DrupalSiteContext drupalSiteContext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        drupalSiteContext = new DrupalSiteContextImpl(drupalSiteUrl, privateKey);
    }

    public void handleRefresh() {
        ProgressDialog.Builder pb = new ProgressDialog.Builder(this);
        pb.setMessage("Loading...");
        AlertDialog progressDialog = pb.create();
        progressDialog.setOwnerActivity(this);
        progressDialog.show();
        try {
            DrupalUser drupalUser = drupalSiteContext.login("admin", "nothing");
            if (drupalUser != null) {
                createBanner(drupalUser);
            }
            progressDialog.dismiss();
        } catch (DrupalLoginException e) {
            progressDialog.dismiss();
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        } catch (DrupalFetchException e) {
            progressDialog.dismiss();
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
    }

    private void createBanner(DrupalUser drupalUser) {
        TextView view = new TextView(this);
        StringBuffer sb = new StringBuffer();
        sb.append("Logged in as: ");
        sb.append(drupalUser.getName()).append("\n");
        sb.append("Email: ").append(drupalUser.getMail()).append("\n\n");

        view.setText(sb.toString());
        setContentView(view);
    }

    public void loginButtonOnClickHandler(View target) {
        handleRefresh();
    }
}
