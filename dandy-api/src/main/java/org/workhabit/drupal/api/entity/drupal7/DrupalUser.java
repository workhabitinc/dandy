package org.workhabit.drupal.api.entity.drupal7;

import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 9:37:57 PM
 */
public class DrupalUser implements DrupalEntity {
    private int uid;
    private String name;
    private String mail;
    private Date access;
    private Date created;
    private Date login;
    private String password;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public Date getAccess() {
        return access;
    }

    public void setAccess(Date access) {
        this.access = access;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLogin() {
        return login;
    }

    public void setLogin(Date login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings({"SameReturnValue"})
    public String getIdFieldName() {
        return "uid";
    }

    public String getId() {
        return String.valueOf(getUid());
    }
}
