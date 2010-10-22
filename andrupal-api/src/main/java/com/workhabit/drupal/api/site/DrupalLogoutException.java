package com.workhabit.drupal.api.site;

import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 11:20:17 AM
 */
public class DrupalLogoutException extends Exception {
    public DrupalLogoutException(Exception e) {
        this.initCause(e);
    }
}
