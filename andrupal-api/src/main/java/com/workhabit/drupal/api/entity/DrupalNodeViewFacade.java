package com.workhabit.drupal.api.entity;

import java.util.Date;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 16, 2010, 4:29:32 PM
 */
public class DrupalNodeViewFacade extends DrupalNode {
    private DrupalNode drupalNode;

    @Override
    public String getTitle() {
        return drupalNode.getTitle();
    }

    @Override
    public String getBody() {
        return drupalNode.getBody();
    }

    @Override
    public Date getCreated() {
        return drupalNode.getCreated();
    }

    @Override
    public Date getUpdated() {
        return drupalNode.getUpdated();
    }

    @Override
    public Map<String, DrupalField> getFields() {
        return drupalNode.getFields();
    }

    @Override
    public void setTitle(String title) {
        drupalNode.setTitle(title);
    }

    @Override
    public void setBody(String body) {
        drupalNode.setBody(body);
    }

    @Override
    public void setCreated(Date created) {
        drupalNode.setCreated(created);
    }

    @Override
    public void setUpdated(Date updated) {
        drupalNode.setUpdated(updated);
    }

    @Override
    public void setFields(Map<String, DrupalField> fields) {
        drupalNode.setFields(fields);
    }

    @Override
    public int getNid() {
        return drupalNode.getNid();
    }

    @Override
    public void setNid(int nid) {
        drupalNode.setNid(nid);
    }

    @Override
    public String getSiteUrl() {
        return drupalNode.getSiteUrl();
    }

    public DrupalNodeViewFacade() {
        this.drupalNode = new DrupalNode();
    }

    public DrupalNodeViewFacade(DrupalNode drupalNode) {
        this.drupalNode = drupalNode;
    }

    public void setNode_Title(String title) {
        drupalNode.setTitle(title);
    }

    public void setNode_Created(Date created) {
        drupalNode.setCreated(created);
    }
}
