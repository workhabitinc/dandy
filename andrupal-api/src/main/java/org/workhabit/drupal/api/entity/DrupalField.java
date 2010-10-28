package org.workhabit.drupal.api.entity;

import org.workhabit.drupal.api.annotations.IdFieldName;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:52 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@IdFieldName("id")
public class DrupalField implements DrupalEntity, Serializable {
    @Id
    private int id;
    @Column
    private String name;

    @Column
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
