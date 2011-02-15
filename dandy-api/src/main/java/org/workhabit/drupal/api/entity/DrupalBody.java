package org.workhabit.drupal.api.entity;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:46 PM
 */
public class DrupalBody
{
    private List<DrupalBodyValue> und;

    public List<DrupalBodyValue> getUnd()
    {
        return und;
    }

    public void setUnd(List<DrupalBodyValue> und)
    {
        this.und = und;
    }
}
