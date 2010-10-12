package com.workhabit.drupal.entity;

import com.workhabit.drupal.site.DrupalLoginException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 9:37:57 PM
 */
public class DrupalUser {
    private int uid;
    private String name;
    private String mail;
    private Date access;
    private Date created;
    private Date login;

    public DrupalUser(JSONObject objectResult, String siteUrl) throws DrupalLoginException {
        try {
            uid = objectResult.getInt("uid");
            name = objectResult.getString("name");
            mail = objectResult.getString("mail");
            access = new Date(objectResult.getLong("access"));
            created = new Date(objectResult.getLong("created"));
            login = new Date(objectResult.getLong("login"));

        } catch (JSONException e) {
            throw new DrupalLoginException(objectResult, siteUrl);
        }
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public Date getAccess() {
        return access;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLogin() {
        return login;
    }
}
