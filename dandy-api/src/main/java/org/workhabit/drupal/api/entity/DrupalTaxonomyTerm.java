package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:59:12 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@IdFieldName("tid")
@DatabaseTable(tableName = "DrupalTaxonomyTerm")
public class DrupalTaxonomyTerm implements DrupalEntity {
    @DatabaseField(id = true)
    private int tid;
    @DatabaseField(canBeNull = false)
    private int vid;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = true)
    private String description;
    @DatabaseField(canBeNull = true)
    private int weight;
    @DatabaseField(canBeNull = true)
    private int depth;
    @DatabaseField(canBeNull = true)
    private List<Integer> parents;
    @DatabaseField(canBeNull = true)
    private int nodeCount;

    public DrupalTaxonomyTerm() {

    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int node_count) {
        this.nodeCount = node_count;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Integer> getParents() {
        return parents;
    }

    public void setParents(List<Integer> parents) {
        this.parents = parents;
    }

    public String getId() {
        return Integer.toString(tid);
    }
}
