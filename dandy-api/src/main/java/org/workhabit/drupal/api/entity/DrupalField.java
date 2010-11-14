package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:52 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@DatabaseTable(tableName = "DrupalField")
@IdFieldName("id")
public class DrupalField implements DrupalEntity, Serializable {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = true)
    private ArrayList<HashMap<String, String>> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HashMap<String, String>> getValues() {
        return values;
    }

    public void setValues(ArrayList<HashMap<String, String>> values) {
        this.values = values;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
