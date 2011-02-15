package org.workhabit.drupal.api.site.impl;

import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/14/11, 6:47 PM
 */
public interface DrupalSiteContextInstanceState extends Serializable
{
    ArrayList<GenericCookie> getCookies();

    DrupalUser getUser();
}
