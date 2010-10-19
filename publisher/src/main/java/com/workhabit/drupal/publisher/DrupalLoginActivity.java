package com.workhabit.drupal.publisher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.workhabit.drupal.api.entity.DrupalUser;
import com.workhabit.drupal.api.site.DrupalFetchException;
import com.workhabit.drupal.api.site.DrupalLoginException;
import com.workhabit.drupal.api.site.DrupalSiteContext;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:01:59 PM
 */
public class DrupalLoginActivity extends AbstractAnDrupalActivity implements View.OnClickListener {

    private DrupalSiteContext drupalSiteContext;
    private AlertDialog.Builder progressDialogBuilder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialogBuilder = new ProgressDialog.Builder(this);
        String drupalSiteUrl = getResources().getString(R.string.drupal_site_url);
        String privateKey = getResources().getString(R.string.drupal_private_key);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        findViewById(R.id.login_button).setOnClickListener(this);
        drupalSiteContext = AnDrupalApplication.getDrupalSiteContext();
    }

    public void handleRefresh() {
        progressDialogBuilder.setMessage("Logging In...");
        AlertDialog progressDialog = progressDialogBuilder.create();
        progressDialog.setOwnerActivity(this);
        progressDialog.show();
        try {
            DrupalUser drupalUser = doLogin();
            progressDialog.dismiss();
            if (drupalUser != null) {
                doLoginSuccess();
            } else {
                doLoginFailed();
            }
        } catch (DrupalLoginException e) {
            progressDialog.dismiss();
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        } catch (DrupalFetchException e) {
            progressDialog.dismiss();
            DrupalDialogHandler.showMessageDialog(this, e.getMessage());
        }
    }

    private void doLoginFailed() {
        // login failed
        //
        progressDialogBuilder.setTitle(R.string.invalid_login_title);
        progressDialogBuilder.setMessage(R.string.invalid_login_message);
        AlertDialog progressDialog = progressDialogBuilder.create();
        progressDialog.show();
    }

    private void doLoginSuccess() {
        Intent intent = new Intent(this, DrupalTaxonomyListActivity.class);
        this.startActivity(intent);
    }

    private DrupalUser doLogin() throws DrupalLoginException, DrupalFetchException {
        String username = ((EditText) findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
        DrupalUser drupalUser = drupalSiteContext.login(username, password);
        return drupalUser;
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

    public void onClick(View view) {
        handleRefresh();
    }
}
