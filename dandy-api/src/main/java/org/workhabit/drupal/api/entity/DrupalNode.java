package org.workhabit.drupal.api.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.workhabit.drupal.api.annotations.IdFieldName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:05 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@DatabaseTable(tableName = "DrupalNode")
@IdFieldName("nid")
public class DrupalNode implements DrupalEntity {
    @DatabaseField(id = true)
    private int nid;
    @DatabaseField(canBeNull = false)
    private int uid;
    @DatabaseField(canBeNull = false)
    private boolean status;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField(canBeNull = true)
    private int comment;
    @DatabaseField(canBeNull = true)
    private Boolean promote;
    @DatabaseField(canBeNull = true)
    private Boolean moderate;
    @DatabaseField(canBeNull = true)
    private Boolean sticky;
    @DatabaseField(canBeNull = true)
    private String body;
    @DatabaseField(canBeNull = true)
    private String teaser;
    @DatabaseField(canBeNull = true)
    private String log;
    @DatabaseField(canBeNull = false)
    private Date revisionTimestamp;
    @DatabaseField(canBeNull = false)
    private int format;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = true)
    private String picture;
    @DatabaseField(canBeNull = true)
    private String data;
    @DatabaseField(canBeNull = true)
    private Date lastCommentTimestamp;
    @DatabaseField(canBeNull = true)
    private String lastCommentName;
    @DatabaseField(canBeNull = false)
    private int commentCount;

    // no need to serialize taxonomy
    @DatabaseField(canBeNull = true)
    private HashMap<Integer, DrupalTaxonomyTerm> taxonomy;
    @DatabaseField(canBeNull = false)
    private Date created;
    @DatabaseField(canBeNull = false)
    private Date changed;

    @DatabaseField(canBeNull = true)
    private ArrayList<DrupalField> fields;

    public DrupalNode() {

    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public Boolean isPromote() {
        return promote;
    }

    public void setPromote(Boolean promote) {
        this.promote = promote;
    }

    public Boolean isModerate() {
        return moderate;
    }

    public void setModerate(Boolean moderate) {
        this.moderate = moderate;
    }

    public Boolean isSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getRevisionTimestamp() {
        return revisionTimestamp;
    }

    public void setRevisionTimestamp(Date revisionTimestamp) {
        this.revisionTimestamp = revisionTimestamp;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getLastCommentTimestamp() {
        return lastCommentTimestamp;
    }

    public void setLastCommentTimestamp(Date lastCommentTimestamp) {
        this.lastCommentTimestamp = lastCommentTimestamp;
    }

    public String getLastCommentName() {
        return lastCommentName;
    }

    public void setLastCommentName(String lastCommentName) {
        this.lastCommentName = lastCommentName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public HashMap<Integer, DrupalTaxonomyTerm> getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(HashMap<Integer, DrupalTaxonomyTerm> taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public ArrayList<DrupalField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<DrupalField> fields) {
        this.fields = fields;
    }

    public String getId() {
        return Integer.toString(getNid());
    }
}
