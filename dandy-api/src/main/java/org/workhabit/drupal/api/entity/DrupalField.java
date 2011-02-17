package org.workhabit.drupal.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:52 PM
 */
public class DrupalField implements DrupalEntity, Serializable
{
    private int id;
    private String name;

    private ArrayList<HashMap<String, String>> values;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<HashMap<String, String>> getValues()
    {
        return values;
    }

    public void setValues(ArrayList<HashMap<String, String>> values)
    {
        this.values = values;
    }

    public String getId()
    {
        return Integer.toString(id);
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
