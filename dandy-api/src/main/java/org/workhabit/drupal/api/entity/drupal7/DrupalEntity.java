package org.workhabit.drupal.api.entity.drupal7;

import java.io.Serializable;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:34:44 PM
 */
public interface DrupalEntity extends Serializable {
    //marker interface
    @SuppressWarnings({"UnusedDeclaration"})
    String getId();
}
