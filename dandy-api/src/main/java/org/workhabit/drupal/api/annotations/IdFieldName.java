package org.workhabit.drupal.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 1:09:11 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IdFieldName {
    String value();
}
