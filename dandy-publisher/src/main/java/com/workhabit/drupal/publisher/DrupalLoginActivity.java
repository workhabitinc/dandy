package com.workhabit.drupal.publisher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.workhabit.drupal.publisher.support.DrupalDialogHandler;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:01:59 PM
 */
public class DrupalLoginActivity extends AbstractDandyActivity implements View.OnClickListener
{

    private DrupalSiteContext drupalSiteContext;
    private AlertDialog.Builder progressDialogBuilder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        progressDialogBuilder = new ProgressDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        findViewById(R.id.login_button).setOnClickListener(this);
        drupalSiteContext = DandyApplication.getDrupalSiteContext(savedInstanceState);
    }

    public void handleRefresh()
    {
        progressDialogBuilder.setMessage("Logging In...");
        final AlertDialog progressDialog = progressDialogBuilder.create();
        progressDialog.setOwnerActivity(this);
        progressDialog.show();
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                DrupalUser drupalUser = null;
                try {
                    drupalUser = doLogin();
                    progressDialog.dismiss();
                    if (drupalUser != null) {
                        doLoginSuccess();
                    }
                    else {
                        doLoginFailed();
                    }
                } catch (DrupalLoginException e) {
                    progressDialog.dismiss();
                    DrupalDialogHandler.showMessageDialog(progressDialog.getOwnerActivity(), e.getMessage());
                } catch (DrupalFetchException e) {
                    progressDialog.dismiss();
                    DrupalDialogHandler.showMessageDialog(progressDialog.getOwnerActivity(), e.getMessage());
                }

            }
        });
        t.start();
    }

    private void doLoginFailed()
    {
        // login failed
        //
        progressDialogBuilder.setTitle(R.string.invalid_login_title);
        progressDialogBuilder.setMessage(R.string.invalid_login_message);
        AlertDialog progressDialog = progressDialogBuilder.create();
        progressDialog.show();
    }

    private void doLoginSuccess()
    {
        Intent intent = new Intent(this, DrupalTaxonomyListActivity.class);
        this.startActivity(intent);
    }

    private DrupalUser doLogin() throws DrupalLoginException, DrupalFetchException
    {
        String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
        return drupalSiteContext.login(username, password);
    }

    public void onClick(View view)
    {
        handleRefresh();
    }
}
