package org.workhabit.drupal.api.entity;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:59:12 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DrupalTaxonomyTerm implements DrupalEntity {
    private int vid;
    private int tid;
    private String name;
    private String description;
    private int weight;
    private int depth;
    private List<Integer> parents;
    private int nodeCount;


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
}
