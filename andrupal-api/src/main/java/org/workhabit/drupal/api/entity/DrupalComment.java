package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 4:40:51 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@DatabaseTable(tableName = "DrupalComment")
@IdFieldName("cid")
public class DrupalComment {
    @DatabaseField(canBeNull = false)
    private int nid;
    @DatabaseField(canBeNull = false)
    private int cid;
    @DatabaseField(canBeNull = false)
    private int uid;
    @DatabaseField(canBeNull = true)
    private String title;
    @DatabaseField(canBeNull = true)
    private String body;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
