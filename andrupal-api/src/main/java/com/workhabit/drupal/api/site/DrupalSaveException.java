package com.workhabit.drupal.api.site;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:46:47 AM
 */
public class DrupalSaveException extends DrupalFetchException {

    public DrupalSaveException(JSONObject objectResult, String siteUrl) {
        super(objectResult, siteUrl);
    }

    public DrupalSaveException(Exception e) {
        super(e);
    }
}
