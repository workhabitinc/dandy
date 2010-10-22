package com.workhabit.drupal.api.site.impl;

import android.util.Log;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workhabit.drupal.api.entity.DrupalComment;
import com.workhabit.drupal.api.entity.DrupalNode;
import com.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import com.workhabit.drupal.api.entity.DrupalUser;
import com.workhabit.drupal.api.json.DrupalJSONObjectSerializer;
import com.workhabit.drupal.api.json.UnixTimeDateAdapter;
import com.workhabit.drupal.api.site.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:21:03 PM
 */
public class DrupalSiteContextImpl implements DrupalSiteContext {

    private DrupalJsonRequestManager manager;

    private String session;
    private DrupalUser user;
    private boolean isConnected;

    private String drupalSiteUrl;
    private String name;
    private FieldNamingStrategy fieldNamingStrategy;

    /**
     * Constructor takes an authentication token to use for the lifecycle of requests for this instance
     *
     * @param drupalSiteUrl site url to connect to
     * @param privateKey    the secret key to use when connecting to Drupal
     */
    public DrupalSiteContextImpl(String drupalSiteUrl, String privateKey) {
        fieldNamingStrategy = new FieldNamingStrategy() {
            public String translateName(Field field) {
                name = field.getName();
                if ("node_title".equals(name)) {
                    return "title";
                }
                if ("node_created".equals(name)) {
                    return "created";
                }
                return name;
            }
        };
        this.drupalSiteUrl = drupalSiteUrl;
        KeyRequestSigningInterceptorImpl requestSigningInterceptor = new KeyRequestSigningInterceptorImpl();
        String drupalDomain = drupalSiteUrl.replaceAll("^http://(.*?)/.*$", "\\1");
        requestSigningInterceptor.setDrupalDomain(drupalDomain);
        requestSigningInterceptor.setPrivateKey(privateKey);
        manager = new DrupalJsonRequestManager();
        manager.setRequestSigningInterceptor(requestSigningInterceptor);
    }

    /**
     * call system.connect on the current instance
     */
    public void connect() throws DrupalFetchException {
        if (!isConnected) {
            try {
                String result = manager.post(drupalSiteUrl + "/services/json", "system.connect", null);
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
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "user.logout", null);
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
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "node.get", data);
            DrupalJSONObjectSerializer<DrupalNode> serializer = new DrupalJSONObjectSerializer<DrupalNode>(DrupalNode.class);
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
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "node.get", data);
            DrupalJSONObjectSerializer<DrupalComment> serializer = new DrupalJSONObjectSerializer<DrupalComment>(DrupalComment.class);
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
            manager.postSigned(drupalSiteUrl + "/services/json", "comment.save", data);
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
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "user.login", data);
            return processLoginResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        try {
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "views.get", data);
            return processGetTermViewResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("vid", 1);
        try {
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "taxonomy.dictionary", data);
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
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "views.get", data);
            DrupalJSONObjectSerializer<DrupalNode> serializer = new DrupalJSONObjectSerializer<DrupalNode>(DrupalNode.class);
            return serializer.unserializeList(result);
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }
        return null;
    }

    private List<DrupalTaxonomyTerm> processGetTermViewResult(String result) throws DrupalFetchException, JSONException {
        DrupalJSONObjectSerializer<DrupalTaxonomyTerm> serializer = new DrupalJSONObjectSerializer<DrupalTaxonomyTerm>(DrupalTaxonomyTerm.class);
        return serializer.unserializeList(result);
    }

    private DrupalUser processLoginResult(String result) throws JSONException, DrupalFetchException, DrupalLoginException {
        JSONObject objectResult = new JSONObject(result);
        Log.i("result", result);
        assertNoErrors(objectResult);

        JSONObject dataObject = objectResult.getJSONObject("#data");
        setSession(dataObject.getString("sessid"));
        user = new DrupalUser(dataObject.getJSONObject("user"), drupalSiteUrl);
        return user;
    }


    public void setSession(String session) {
        this.session = session;
    }

    // convert integer timestamp to date

}
