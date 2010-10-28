package org.workhabit.drupal.api.entity;

import org.workhabit.drupal.api.annotations.IdFieldName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 3:59:12 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@IdFieldName("tid")
@Entity
public class DrupalTaxonomyTerm implements DrupalEntity {
    @Id
    private int tid;
    @Column
    private int vid;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int weight;
    @Column
    private int depth;
    @Column
    private List<Integer> parents;
    @Column
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

    public String getId() {
        return Integer.toString(tid);
    }
}
