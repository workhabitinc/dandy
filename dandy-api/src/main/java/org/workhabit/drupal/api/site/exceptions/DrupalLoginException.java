package org.workhabit.drupal.api.site.exceptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 8:27:56 PM
 */
@SuppressWarnings({"CanBeFinal"})
public class DrupalLoginException extends Exception {

    private Throwable t;

    public DrupalLoginException(Throwable t) {
        this.t = t;
    }

    @Override
    public String getMessage() {
        return t.getMessage();
    }

    @Override
    public Throwable getCause()
    {
        return t.getCause();
    }
}
