package org.workhabit.drupal.api.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:52 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DrupalField implements DrupalEntity, Serializable {
    private String name;
    private List<Map<String, String>> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, String>> values) {
        this.values = values;
    }
    
}
