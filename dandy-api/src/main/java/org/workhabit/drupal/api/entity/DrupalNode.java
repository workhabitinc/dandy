package org.workhabit.drupal.api.entity;

import java.util.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:25:05 PM
 */
public class DrupalNode implements DrupalEntity
{
    private int nid;
    private int uid;
    private String type;
    private Boolean status;
    private String title;
    private int comment;
    private Boolean promote;
    private Boolean moderate;
    private Boolean sticky;
    private Map<String, List<DrupalBody>> body;
    private String log;
    private Date revisionTimestamp;
    private int format;
    private String name;
    private String picture;
    private String data;
    private Date lastCommentTimestamp;
    private String lastCommentName;
    private int commentCount;
    private String language;

    // no need to serialize taxonomy
    private HashMap<Integer, DrupalTaxonomyTerm> taxonomy;
    private Date created;
    private Date changed;

    private Map<String, DrupalField> fields;

    public DrupalNode()
    {

    }

    public int getNid()
    {
        return nid;
    }

    public void setNid(int nid)
    {
        this.nid = nid;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getComment()
    {
        return comment;
    }

    public void setComment(int comment)
    {
        this.comment = comment;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public Boolean isPromote()
    {
        return promote;
    }

    public void setPromote(Boolean promote)
    {
        this.promote = promote;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public Boolean isModerate()
    {
        return moderate;
    }

    public void setModerate(Boolean moderate)
    {
        this.moderate = moderate;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public Boolean isSticky()
    {
        return sticky;
    }

    public void setSticky(Boolean sticky)
    {
        this.sticky = sticky;
    }

    public Map<String, List<DrupalBody>> getBody()
    {
        return body;
    }

    public void setBody(Map<String, List<DrupalBody>> body)
    {
        this.body = body;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getLog()
    {
        return log;
    }

    public void setLog(String log)
    {
        this.log = log;
    }

    public Date getRevisionTimestamp()
    {
        return revisionTimestamp;
    }

    public void setRevisionTimestamp(Date revisionTimestamp)
    {
        this.revisionTimestamp = revisionTimestamp;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public int getFormat()
    {
        return format;
    }

    public void setFormat(int format)
    {
        this.format = format;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public Date getLastCommentTimestamp()
    {
        return lastCommentTimestamp;
    }

    public void setLastCommentTimestamp(Date lastCommentTimestamp)
    {
        this.lastCommentTimestamp = lastCommentTimestamp;
    }

    public String getLastCommentName()
    {
        return lastCommentName;
    }

    public void setLastCommentName(String lastCommentName)
    {
        this.lastCommentName = lastCommentName;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }

    public HashMap<Integer, DrupalTaxonomyTerm> getTaxonomy()
    {
        return taxonomy;
    }

    public void setTaxonomy(HashMap<Integer, DrupalTaxonomyTerm> taxonomy)
    {
        this.taxonomy = taxonomy;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getChanged()
    {
        return changed;
    }

    public void setChanged(Date changed)
    {
        this.changed = changed;
    }

    public Map<String, DrupalField> getFields()
    {
        return fields;
    }

    public void setFields(Map<String, DrupalField> fields)
    {
        this.fields = fields;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public DrupalField getField(String fieldName)
    {
        if (fields == null) {
            return null;
        }
        if (!fields.containsKey(fieldName)) {
            return null;
        }
        return fields.get(fieldName);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void addField(String fieldName, HashMap<String, String> values)
    {
        if (this.fields == null) {
            this.fields = new HashMap<String, DrupalField>();
        }
        if (this.fields.containsKey(fieldName)) {
            DrupalField field = fields.get(fieldName);
            field.getValues().add(values);
        }
        else {
            DrupalField drupalField = new DrupalField();
            drupalField.setName(fieldName);

            ArrayList<HashMap<String, String>> valueList = new ArrayList<HashMap<String, String>>();
            valueList.add(values);
            drupalField.setValues(valueList);
            this.fields.put(fieldName, drupalField);
        }
    }

    public String getId()
    {
        return Integer.toString(getNid());
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}
