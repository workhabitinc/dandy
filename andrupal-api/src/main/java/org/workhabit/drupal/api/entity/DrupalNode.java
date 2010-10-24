package org.workhabit.drupal.api.entity;

import java.util.Date;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:05 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DrupalNode {
    private int nid;
    private int uid;
    private boolean status;
    private String title;
    private int comment;
    private boolean promote;
    private boolean moderate;
    private boolean sticky;
    private String body;
    private String teaser;
    private String log;
    private Date revisionTimestamp;
    private int format;
    private String name;
    private String picture;
    private String data;
    private Date lastCommentTimestamp;
    private String lastCommentName;
    private int commentCount;
    private Map<Integer, DrupalTaxonomyTerm> taxonomy;
    private Date created;
    private Date changed;
    private Map<String, DrupalField> fields;

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

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public boolean isModerate() {
        return moderate;
    }

    public void setModerate(boolean moderate) {
        this.moderate = moderate;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
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

    public Map<Integer, DrupalTaxonomyTerm> getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Map<Integer, DrupalTaxonomyTerm> taxonomy) {
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

    public Map<String, DrupalField> getFields() {
        return fields;
    }

    public void setFields(Map<String, DrupalField> fields) {
        this.fields = fields;
    }
}
