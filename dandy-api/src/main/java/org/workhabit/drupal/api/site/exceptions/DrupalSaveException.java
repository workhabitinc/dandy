package org.workhabit.drupal.api.site.exceptions;

import org.json.JSONObject;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:46:47 AM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DrupalSaveException extends DrupalFetchException {

    public DrupalSaveException(JSONObject objectResult) {
        super(objectResult);
    }

    public DrupalSaveException(Exception e) {
        super(e);
    }
}
