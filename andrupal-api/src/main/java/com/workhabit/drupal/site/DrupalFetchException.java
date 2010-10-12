package com.workhabit.drupal.site;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 25, 2010, 5:15:46 PM
 */
public class DrupalFetchException extends Throwable {
    private JSONObject objectResult;
    private String siteUrl;
    private String message;

    public DrupalFetchException(JSONObject objectResult, String siteUrl) {

        this.objectResult = objectResult;
        this.siteUrl = siteUrl;
        try {
            this.message = this.objectResult.getString("#data");
        } catch (JSONException e) {
            this.message = e.getMessage();
        }
    }
    public DrupalFetchException(Exception e) {
        this.message = String.format("%s: %s", e.getClass().getName(), e.getMessage());
    }

    public JSONObject getObjectResult() {
        return objectResult;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getMessage() {
        return message;
    }
}
