package com.workhabit.drupal.publisher.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 8:10:55 PM
 */
public class DrupalDialogHandler {
    public static void showMessageDialog(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("An Error occurred!");
        builder.setMessage(message).setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss(); // close the dialog
                    }
                }
        );
        AlertDialog alert = builder.create();
        alert.setOwnerActivity(activity);
        alert.show();
    }
}
