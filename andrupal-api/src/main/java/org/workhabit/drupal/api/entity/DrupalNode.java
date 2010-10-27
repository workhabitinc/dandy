package org.workhabit.drupal.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:05 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@Entity
public class DrupalNode implements DrupalEntity {
    @Id
    private int nid;
    @Column(nullable = false)
    private int uid;
    @Column(nullable = false)
    private boolean status;
    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private int comment;
    @Column(nullable = true)
    private Boolean promote;
    @Column(nullable = true)
    private Boolean moderate;
    @Column(nullable = true)
    private Boolean sticky;
    @Column(nullable = true)
    private String body;
    @Column(nullable = true)
    private String teaser;
    @Column(nullable = true)
    private String log;
    @Column(nullable = false)
    private Date revisionTimestamp;
    @Column(nullable = false)
    private int format;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String picture;
    @Column(nullable = true)
    private String data;
    @Column(nullable = true)
    private Date lastCommentTimestamp;
    @Column(nullable = true)
    private String lastCommentName;
    @Column(nullable = false)
    private int commentCount;
    @ManyToMany
    private HashMap<Integer, DrupalTaxonomyTerm> taxonomy;
    @Column(nullable = false)
    private Date created;
    @Column(nullable = false)
    private Date changed;
    @Column(nullable = true)
    private HashMap<String, DrupalField> fields;

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

    public HashMap<String, DrupalField> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, DrupalField> fields) {
        this.fields = fields;
    }
}
