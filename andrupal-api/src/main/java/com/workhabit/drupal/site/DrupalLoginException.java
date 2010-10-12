package com.workhabit.drupal.site;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 8:27:56 PM
 */
public class DrupalLoginException extends Throwable {
    private JSONObject objectResult;
    private String message;

    public DrupalLoginException(JSONObject objectResult, String siteUrl) {

        this.objectResult = objectResult;
        try {
            this.message = "An error occurred connecting to " + siteUrl + ": " + objectResult.getString("#data");
        } catch (JSONException e) {
            this.message = "Unable to unmarshal JSON Object: " + e.getMessage();
        }
    }

    public JSONObject getObjectResult() {
        return objectResult;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
