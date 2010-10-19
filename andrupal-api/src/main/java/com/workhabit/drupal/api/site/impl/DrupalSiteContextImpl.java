package com.workhabit.drupal.api.site.impl;

import android.util.Log;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workhabit.drupal.api.entity.DrupalComment;
import com.workhabit.drupal.api.entity.DrupalNode;
import com.workhabit.drupal.api.entity.DrupalTaxonomyTerm;
import com.workhabit.drupal.api.entity.DrupalUser;
import com.workhabit.drupal.api.site.DrupalFetchException;
import com.workhabit.drupal.api.site.DrupalJsonRequestManager;
import com.workhabit.drupal.api.site.DrupalLoginException;
import com.workhabit.drupal.api.site.DrupalSiteContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
     * @param privateKey
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
            } catch (Exception e) {
                throw new DrupalFetchException(e);
            }
            // TODO: implement
            isConnected = true;
        }
    }

    public void logout() {
        // todo: implement
    }

    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        try {
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "views.get", data);
            return processGetNodeViewResult(result);
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }
        return null;
    }

    private List<DrupalNode> processGetNodeViewResult(String result) throws JSONException, DrupalFetchException {
        JSONObject objectResult = new JSONObject(result);
        assertNoErrors(objectResult);
        List<DrupalNode> nodes = new ArrayList<DrupalNode>();
        JSONArray nodeArray = objectResult.getJSONArray("#data");
        for (int i = 0; i < nodeArray.length(); i++) {
            JSONObject nodeObject = nodeArray.getJSONObject(i);
            DrupalNode drupalNode = createNodeObjectFromJsonView(nodeObject);
            nodes.add(drupalNode);
        }
        return nodes;
    }

    private DrupalNode createNodeObjectFromJsonView(JSONObject nodeObject) throws JSONException {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingStrategy(fieldNamingStrategy);
        Gson gson = builder.create();
        return gson.fromJson(nodeObject.toString(), DrupalNode.class);

    }

    protected void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult, drupalSiteUrl);
        }
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        data.put("sessid", session);
        try {
            String result = manager.postSigned(drupalSiteUrl + "/services/json", "node.get", data);
            return processGetNodeResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    private DrupalNode processGetNodeResult(String result) throws JSONException, DrupalFetchException {
        JSONObject objectResult = new JSONObject(result);
        assertNoErrors(objectResult);
        JSONObject dataObject = objectResult.getJSONObject("#data");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(dataObject.toString(), DrupalNode.class);
    }

    public DrupalComment getComment(int nid, int cid) throws DrupalFetchException {
        connect();
        // TODO: implement
        return null;
    }

    public void saveComment(DrupalComment comment) throws DrupalFetchException {
        connect();
        // TODO: Implement
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

    private List<DrupalTaxonomyTerm> processGetTermViewResult(String result) throws DrupalFetchException, JSONException {
        JSONObject objectResult = new JSONObject(result);
        assertNoErrors(objectResult);
        List<DrupalTaxonomyTerm> terms = new ArrayList<DrupalTaxonomyTerm>();
        Log.i("result", result);
        JSONArray termArray = objectResult.getJSONArray("#data");
        for (int i = 0; i < termArray.length(); i++) {
            JSONObject termObject = termArray.getJSONObject(i);
            DrupalTaxonomyTerm drupalTerm = createTermObjectFromJsonView(termObject);
            terms.add(drupalTerm);
        }
        return terms;
    }

    private DrupalTaxonomyTerm createTermObjectFromJsonView(JSONObject termObject) throws JSONException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(termObject.toString(), DrupalTaxonomyTerm.class);
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

}
