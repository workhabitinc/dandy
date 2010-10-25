package org.workhabit.drupal.api.site.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.workhabit.drupal.api.entity.DrupalComment;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import org.workhabit.drupal.api.entity.DrupalUser;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.site.*;
import org.workhabit.drupal.http.JsonRequestManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:21:03 PM
 */
public class DrupalSiteContextImpl implements DrupalSiteContext {

    private JsonRequestManager jsonRequestManager;

    private String session;

    private DrupalUser user;
    private boolean isConnected;
    private String drupalSiteUrl;

    /**
     * Constructor takes an authentication token to use for the lifecycle of requests for this instance
     *
     * @param drupalSiteUrl site url to connect to
     */
    public DrupalSiteContextImpl(String drupalSiteUrl) {
        this.drupalSiteUrl = drupalSiteUrl;
    }

    /**
     * call system.connect on the current instance
     */
    public void connect() throws DrupalFetchException {
        if (!isConnected) {
            try {
                String result = jsonRequestManager.post(drupalSiteUrl + "/services/json", "system.connect", null);
                JSONObject object = new JSONObject(result);
                if ("true".equals(object.getString("#error"))) {
                    throw new DrupalFetchException(object);
                }
            } catch (Exception e) {
                throw new DrupalFetchException(e);
            }
            isConnected = true;
        }
    }

    public void logout() throws DrupalLogoutException {
        try {
            connect();
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "user.logout", null);
            JSONObject object = new JSONObject(result);
            if ("true".equals(object.getString("#error"))) {
                throw new DrupalFetchException(object);
            }
            setSession(null);
            isConnected = false;
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalLogoutException(e);
        } catch (IOException e) {
            throw new DrupalLogoutException(e);
        } catch (JSONException e) {
            throw new DrupalLogoutException(e);
        } catch (DrupalFetchException e) {
            throw new DrupalLogoutException(e);
        }

    }

    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException {
        return getNodeView(viewName, null);
    }

    protected void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult);
        }
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        data.put("sessid", session);
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "node.get", data);
            DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
            return serializer.unserialize(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalComment getComment(int cid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("cid", cid);

        data.put("sessid", session);
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "node.get", data);
            DrupalJsonObjectSerializer<DrupalComment> serializer = new DrupalJsonObjectSerializer<DrupalComment>(DrupalComment.class);
            return serializer.unserialize(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public void saveComment(DrupalComment comment) throws DrupalFetchException {
        connect();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonComment = gson.toJson(comment);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("comment", jsonComment);
        try {
            jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "comment.save", data);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalSaveException(e);
        } catch (IOException e) {
            throw new DrupalSaveException(e);
        }

    }

    public DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException {
        connect();
        if (session != null && user != null) {
            return user;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", username);
        data.put("password", password);
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "user.login", data);
            return processLoginResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "views.get", data);
            return processGetTermViewResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("vid", 1);
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "taxonomy.dictionary", data);
            return processGetTermViewResult(result);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        if (viewArguments != null) {
            data.put("args", viewArguments);
        }
        try {
            String result = jsonRequestManager.postSigned(drupalSiteUrl + "/services/json", "views.get", data);
            DrupalJsonObjectSerializer<DrupalNode> serializer = new DrupalJsonObjectSerializer<DrupalNode>(DrupalNode.class);
            return serializer.unserializeList(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    private List<DrupalTaxonomyTerm> processGetTermViewResult(String result) throws DrupalFetchException, JSONException {
        DrupalJsonObjectSerializer<DrupalTaxonomyTerm> serializer = new DrupalJsonObjectSerializer<DrupalTaxonomyTerm>(DrupalTaxonomyTerm.class);
        return serializer.unserializeList(result);
    }

    private DrupalUser processLoginResult(String result) throws JSONException, DrupalFetchException, DrupalLoginException {
        JSONObject objectResult = new JSONObject(result);
        assertNoErrors(objectResult);

        JSONObject dataObject = objectResult.getJSONObject("#data");
        setSession(dataObject.getString("sessid"));
        user = new DrupalUser(dataObject.getJSONObject("user"), drupalSiteUrl);
        return user;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setJsonRequestManager(JsonRequestManager jsonRequestManager) {
        this.jsonRequestManager = jsonRequestManager;
    }

}
