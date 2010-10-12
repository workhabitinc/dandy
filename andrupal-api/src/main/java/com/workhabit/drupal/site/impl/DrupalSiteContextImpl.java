package com.workhabit.drupal.site.impl;

import android.util.Log;
import com.workhabit.drupal.entity.DrupalComment;
import com.workhabit.drupal.entity.DrupalNode;
import com.workhabit.drupal.entity.DrupalUser;
import com.workhabit.drupal.site.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:21:03 PM
 */
public class DrupalSiteContextImpl implements DrupalSiteContext {
    private DrupalAuthenticationToken token;

    private DrupalJsonRequestManager manager;

    private String session;
    private DrupalUser user;
    private boolean isConnected;

    String siteUrl;
    private String drupalSiteUrl;

    /**
     * Constructor takes an authentication token to use for the lifecycle of requests for this instance
     *
     * @param drupalSiteUrl
     * @param privateKey
     */
    public DrupalSiteContextImpl(String drupalSiteUrl, String privateKey) {
        this.drupalSiteUrl = drupalSiteUrl;

        DrupalAuthenticationToken token = new DrupalAuthenticationToken(privateKey);
        manager = new DrupalJsonRequestManager(token);
    }

    /**
     * call system.connect on the current instance
     */
    public void connect() throws DrupalFetchException {
        if (!isConnected) {
            try {
                manager.post(drupalSiteUrl + "/services/json", "system.connect", null);
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
            String result = manager.postSigned(siteUrl + "/services/json", "views.get", data);
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
        DrupalNode drupalNode = new DrupalNode(siteUrl);
        drupalNode.setTitle(nodeObject.getString("node_title"));
        drupalNode.setNid(nodeObject.getInt("nid"));
        drupalNode.setCreated(new Date(nodeObject.getLong("node_created") * 1000));
        return drupalNode;
    }

    protected void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult, siteUrl);
        }
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        data.put("sessid", session);
        try {
            String result = manager.postSigned(siteUrl + "/services/json", "node.get", data);
            return processGetNodeResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    private DrupalNode processGetNodeResult(String result) throws JSONException, DrupalFetchException {
        JSONObject objectResult = new JSONObject(result);
        assertNoErrors(objectResult);
        JSONObject dataObject = objectResult.getJSONObject("#data");
        return new DrupalNode(dataObject, siteUrl);
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
            String result = manager.postSigned(siteUrl + "/services/json", "user.login", data);
            return processLoginResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    private DrupalUser processLoginResult(String result) throws JSONException, DrupalFetchException, DrupalLoginException {
        JSONObject objectResult = new JSONObject(result);
        Log.i("result", result);
        assertNoErrors(objectResult);

        JSONObject dataObject = objectResult.getJSONObject("#data");
        setSession(dataObject.getString("sessid"));
        user = new DrupalUser(dataObject.getJSONObject("user"), siteUrl);
        return user;
    }


    public void setSession(String session) {
        this.session = session;
    }

}
