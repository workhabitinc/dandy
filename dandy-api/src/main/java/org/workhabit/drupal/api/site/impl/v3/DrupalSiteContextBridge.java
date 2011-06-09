package org.workhabit.drupal.api.site.impl.v3;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workhabit.drupal.api.entity.drupal7.DrupalNode;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalSaveException;
import org.workhabit.drupal.api.site.exceptions.DrupalServicesResponseException;
import org.workhabit.drupal.http.DrupalServicesRequestManager;
import org.workhabit.drupal.http.ServicesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 6/8/11, 3:58 PM
 */
public class DrupalSiteContextBridge
{
    private static Logger log = LoggerFactory.getLogger(Drupal7SiteContextImpl.class.getSimpleName());
    private DrupalServicesRequestManager requestManager;
    private String rootPath;

    public void setRequestManager(DrupalServicesRequestManager requestManager)
    {
        this.requestManager = requestManager;
    }

    private void assertNoErrors(ServicesResponse response) throws DrupalServicesResponseException
    {
        if (response.getStatusCode() >= 400) {
            throw new DrupalServicesResponseException(response.getStatusCode(), response.getReasonPhrase());
        }
    }

    public ServicesResponse getComments(int nid, int start, int count) throws DrupalFetchException
    {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        if (start != 0) {
            data.put("start", start);
        }

        if (count != 0) {
            data.put("count", count);
        }

        try {
            return requestManager.post(String.format("%s/comment/loadNodeComments.json", rootPath), data);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public void logout(Map<String, Object> data) throws IOException, DrupalServicesResponseException
    {
        log.debug("Logging out current user");

        ServicesResponse response = requestManager.post(rootPath + "/user/logout.json", data);
        assertNoErrors(response);
    }


    public ServicesResponse getNodeView(String viewName, String viewArguments, int offset, int limit) throws DrupalFetchException
    {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Fetching view %s with arguments %s", viewName, viewArguments));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath).append("/views/").append(viewName).append(".json");
        boolean bFirst = true;
        if (viewArguments != null && !"".equals(viewArguments)) {
            sb.append("?args=").append(viewArguments);
            bFirst = false;
        }
        if (bFirst) {
            sb.append("?");
        }
        else {
            sb.append("&");
        }
        sb.append("offset=").append(offset);
        sb.append("&limit=").append(limit);

        try {
            ServicesResponse response = requestManager.getString(sb.toString());
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public ServicesResponse getNode(int nid) throws DrupalFetchException
    {
        if (log.isDebugEnabled()) {
            log.debug("Fetching node with nid " + nid);
        }
        try {
            ServicesResponse response = requestManager.getString(String.format("%s/node/%d.json", rootPath, nid));
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public ServicesResponse getComment(int cid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.getString(String.format("%s/comment/%d.json", rootPath, cid));
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public ServicesResponse saveComment(String commentString) throws DrupalSaveException
    {
        JSONObject data = new JSONObject();
        try {
            data.put("comment", new JSONObject(commentString));
            ServicesResponse response = requestManager.post(rootPath + "/comment.json", data.toString());
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalSaveException(e);
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        } catch (IOException e) {
            throw new DrupalSaveException(e);
        }
    }

    public ServicesResponse login(String username, String password) throws DrupalLoginException
    {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("username", username);
        data.put("password", password);
        // object format should contain sessid, session_name, and user keys.
        try {
            ServicesResponse response = requestManager.post(String.format("%s/user/login.json", rootPath), data);
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalLoginException(e);
        } catch (IOException e) {
            throw new DrupalLoginException(e);
        }
    }


    public ServicesResponse saveNode(DrupalNode node, JSONObject object) throws DrupalFetchException
    {
        ServicesResponse response;
        try {
            if (node.getNid() == 0) {
                response = requestManager.post(String.format("%s/node.json", rootPath), object.toString());
            }
            else {
                response = requestManager.put(String.format("%s/node/%d.json", rootPath, node.getNid()), object.toString());
            }
            assertNoErrors(response);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
        return response;
    }

    public ServicesResponse getUser(int uid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.getString(String.format("%s/user/%d.json", rootPath, uid));
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public ServicesResponse saveFileStream(InputStream inputStream, String fileName) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.postFile(String.format("%s/file.json", rootPath), "files[file]", inputStream, fileName);
            assertNoErrors(response);
            JSONObject obj = new JSONObject(response.getResponseBody());
            String uri = obj.getString("uri");
            response = requestManager.getString(uri + ".json");
            assertNoErrors(response);
            return response;
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }

    }

    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }
}
