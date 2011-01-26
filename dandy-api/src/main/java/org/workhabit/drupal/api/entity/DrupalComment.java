package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 4:40:51 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@DatabaseTable(tableName = "DrupalComment")
@IdFieldName("cid")
public class DrupalComment implements DrupalEntity {
    @DatabaseField(canBeNull = false)
    private int nid;
    @DatabaseField(canBeNull = false)
    private int cid;
    @DatabaseField(canBeNull = false)
    private int uid;
    @DatabaseField(canBeNull = true)
    private String subject;
    @DatabaseField(canBeNull = true)
    private String comment;
    private String name;
    private String mail;
    private Date timestamp;
    private boolean status;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return String.valueOf(cid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
