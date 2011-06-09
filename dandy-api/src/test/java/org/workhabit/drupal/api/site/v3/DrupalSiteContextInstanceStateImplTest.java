package org.workhabit.drupal.api.site.v3;

import org.junit.Test;
import org.workhabit.drupal.api.entity.drupal7.DrupalUser;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceStateImpl;
import org.workhabit.drupal.api.site.support.GenericCookie;
import org.workhabit.drupal.api.site.v3.local.TestData;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 6/8/11, 6:25 PM
 */
public class DrupalSiteContextInstanceStateImplTest
{
    @Test
    public void testInstanceState()
    {
        DrupalSiteContextInstanceStateImpl impl = new DrupalSiteContextInstanceStateImpl();
        impl.setSession(TestData.getTestTitle());
        DrupalUser user = new DrupalUser();
        impl.setUser(user);
        assertSame(user, impl.getUser());
        ArrayList<GenericCookie> cookieList = new ArrayList<GenericCookie>();
        impl.setCookies(cookieList);
        ArrayList<GenericCookie> cookies = impl.getCookies();
        assertSame(cookieList, cookies);
    }
}
