package org.workhabit.drupal.api.site;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 11:20:17 AM
 */
public class DrupalLogoutException extends Exception {
    public DrupalLogoutException(Exception e) {
        this.initCause(e);
    }
}
