package org.workhabit.drupal.api.site.impl;

import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/7/11, 2:17 PM
 */
public interface DrupalSiteContextInstanceState extends Serializable
{
    ArrayList<GenericCookie> getCookies();

    DrupalUser getUser();

    String getSession();
}
