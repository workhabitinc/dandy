package org.workhabit.drupal.api.site.support;

import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 1/19/11, 1:40 PM
 */
public class GenericCookie {
    private String comment;
    private String domain;
    private Date expiryDate;
    private String name;
    private String path;
    private boolean secure;
    private int version;
    private String value;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public String getDomain() {
        return domain;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecure() {
        return secure;
    }

    public int getVersion() {
        return version;
    }

    public String getValue() {
        return value;
    }
}
