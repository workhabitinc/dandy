package org.workhabit.drupal.api.entity.drupal7;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 11:50:24 AM
 */
public class ReadItLater implements DrupalEntity
{

    public static final String WEIGHT_FIELD_NAME = "weight";

    private int id;
    private DrupalNode node;
    private int weight;

    public ReadItLater()
    {

    }

    public String getId()
    {
        return Integer.toString(id);
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public DrupalNode getNode()
    {
        return node;
    }

    public void setNode(DrupalNode node)
    {
        this.node = node;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
