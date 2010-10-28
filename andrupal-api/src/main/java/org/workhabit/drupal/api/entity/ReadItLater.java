package org.workhabit.drupal.api.entity;

import org.workhabit.drupal.api.annotations.IdFieldName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 11:50:24 AM
 */
@Entity
@IdFieldName("id")
public class ReadItLater implements DrupalEntity {

    public static final String WEIGHT_FIELD_NAME = "weight";
    
    @Id
    private int id;
    @OneToOne
    private DrupalNode node;
    @Column
    private int weight;

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
