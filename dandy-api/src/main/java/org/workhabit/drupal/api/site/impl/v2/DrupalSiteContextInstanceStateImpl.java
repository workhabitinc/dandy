package org.workhabit.drupal.api.site.impl.v2;

import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/7/11, 2:20 PM
 */
public class DrupalSiteContextInstanceStateImpl implements DrupalSiteContextInstanceState
{
    private ArrayList<GenericCookie> cookies;
    private DrupalUser user;

    public void setCookies(ArrayList<GenericCookie> cookies)
    {
        this.cookies = cookies;
    }

    public void setUser(DrupalUser user)
    {
        this.user = user;
    }

    public ArrayList<GenericCookie> getCookies()
    {
        return cookies;
    }

    public DrupalUser getUser()
    {
        return user;
    }
}
