package com.workhabit.drupal.api.site;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:15:46 PM
 */
public class DrupalFetchException extends Exception {
    private JSONObject objectResult;
    private String message;

    public DrupalFetchException(JSONObject objectResult) {

        this.objectResult = objectResult;
        try {
            this.message = this.objectResult.getString("#data");
        } catch (JSONException e) {
            this.message = e.getMessage();
        }
    }
    public DrupalFetchException(Exception e) {
        initCause(e);
        this.message = String.format("%s: %s", e.getClass().getName(), e.getMessage());
    }

    public JSONObject getObjectResult() {
        return objectResult;
    }


    public String getMessage() {
        return message;
    }
}
