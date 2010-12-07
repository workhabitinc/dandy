package org.workhabit.drupal.api.site.impl.v3;

import org.json.JSONException;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializerFactory;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 12/7/10, 10:45 AM
 */
public class DrupalSiteContextV3Impl implements DrupalSiteContext {
    private static final String SERVICES_ENDPOINT = "services/dandy";
    private DrupalServicesRequestManager requestManager;
    private final String siteUrl;

    public DrupalSiteContextV3Impl(String url) {
        this.siteUrl = url;
    }

    public void setRequestManager(DrupalServicesRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void connect() throws DrupalFetchException {
        Map<String,Object> data = new HashMap<String, Object>();
        // TODO: username/password auth
        //
        try {
            requestManager.post(siteUrl + "/" + SERVICES_ENDPOINT, "system/connect", data, false);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public void logout() throws DrupalLogoutException {
        Map<String,Object> data = new HashMap<String, Object>();
        try {
            requestManager.post(siteUrl + "/" + SERVICES_ENDPOINT, "user/logout", data, false);
        } catch (IOException e) {
            throw new DrupalLogoutException(e);
        }
    }

    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException {
        // TODO: implement
        return null;
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException {
        // TODO: implement
        return null;
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException {
        connect();
        try {
            String path = String.format("%s/%s/node/%d.json", siteUrl, SERVICES_ENDPOINT, nid);
            String result = requestManager.getString(path);
            DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
            return serializer.unserialize(result);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalComment getComment(int cid) throws DrupalFetchException {
        String path = String.format("%s/%s/comment/%d.json", siteUrl, SERVICES_ENDPOINT, cid);
        try {
            String result = requestManager.getString(path);
            DrupalJsonObjectSerializer<DrupalComment> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalComment.class);
            return serializer.unserialize(result);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public void saveComment(DrupalComment comment) throws DrupalFetchException {
        //TODO: implement
    }

    public DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException {
        //TODO: implement
        return null;
    }

    public List<DrupalTaxonomyTerm> getTermView(String s) throws DrupalFetchException {
        // TODO: implement
        return null;
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException {
        // TODO: implement
        return null;
    }

    public void registerNewUser(String username, String password, String email) {
        // TODO: implement
    }

    public int saveFile(byte[] bytes, String fileName) throws DrupalFetchException {
        // TODO: implement
        return 0;
    }

    public List<DrupalComment> getComments(int nid) throws DrupalFetchException {
        // TODO: implement
        return null;
    }

    public InputStream getFileStream(String filepath) throws IOException {
        // TODO: implement
        return null;
    }

    public String getFileDirectoryPath() throws DrupalFetchException {
        // TODO: implement
        return null;
    }
}
