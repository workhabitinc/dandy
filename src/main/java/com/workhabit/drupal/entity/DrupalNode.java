package com.workhabit.drupal.entity;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:05 PM
 */
public class DrupalNode {
    private int nid;
    private String title;
    private String body;
    private Date created;
    private Date updated;
    private Map<String, DrupalField> fields;
    private String siteUrl;

    public DrupalNode(JSONObject jsonObject, String siteUrl) {
        try {
            title = jsonObject.getString("title");
            body = jsonObject.getString("body").replaceAll("\r\n", "\n");
            created = new Date(jsonObject.getLong("created") * 1000);
        } catch (JSONException e) {
            Log.e("json", e.getMessage(), e);
        }
    }

    public DrupalNode(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public Map<String, DrupalField> getFields() {
        return fields;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setFields(Map<String, DrupalField> fields) {
        this.fields = fields;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getSiteUrl() {
        return siteUrl;
    }
}
