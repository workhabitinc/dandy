package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 11:50:24 AM
 */
@IdFieldName("id")
@DatabaseTable(tableName = "ReadItLater")
public class ReadItLater implements DrupalEntity {

    public static final String WEIGHT_FIELD_NAME = "weight";

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true)
    private DrupalNode node;
    @DatabaseField(canBeNull = false)
    private int weight;

    public ReadItLater() {

    }

    public String getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public DrupalNode getNode() {
        return node;
    }

    public void setNode(DrupalNode node) {
        this.node = node;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
