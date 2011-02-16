package org.workhabit.drupal.api.site.impl.v3;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workhabit.drupal.api.entity.*;
import org.workhabit.drupal.api.json.BooleanAdapter;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializerFactory;
import org.workhabit.drupal.api.json.UnixTimeDateAdapter;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.*;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.support.GenericCookie;
import org.workhabit.drupal.http.DrupalServicesRequestManager;
import org.workhabit.drupal.http.ServicesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:34 PM
 */
public class DrupalSiteContextV3Impl implements DrupalSiteContext
{
    private static Logger log = LoggerFactory.getLogger(DrupalSiteContextV3Impl.class);
    private DrupalServicesRequestManager requestManager;
    private String drupalSiteUrl;
    private String endpoint;
    private String rootPath;
    private DrupalJsonObjectSerializer<DrupalNode> nodeSerializer;
    private DrupalJsonObjectSerializer<DrupalUser> userObjectSerializer;
    private String session;
    private DrupalUser currentUser;

    public DrupalSiteContextV3Impl(String drupalSiteUrl, String endpoint)
    {
        this.drupalSiteUrl = drupalSiteUrl;
        this.endpoint = endpoint;
        this.rootPath = String.format("%s/%s", drupalSiteUrl, endpoint);
        nodeSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        userObjectSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalUser.class);
        log.debug("initialized new Drupal Site Context");
    }

    public void setRequestManager(DrupalServicesRequestManager requestManager)
    {
        this.requestManager = requestManager;
    }

    public void connect() throws DrupalFetchException
    {
        // TODO
    }

    public void logout() throws DrupalLogoutException
    {
        Map<String,Object> data = new HashMap<String, Object>();
        try {
            this.currentUser = null;
            this.session = null;
            requestManager.post(rootPath + "/user/logout.json", data);
        } catch (IOException e) {
            log.error("exception", e);
            throw new DrupalLogoutException(e);
        }
    }

    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments, int offset, int limit) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.getString(rootPath + "/node/" + nid + ".json");
            log.debug(response.getResponseBody());
            return nodeSerializer.unserialize(response.getResponseBody());
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalComment getComment(int cid) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int saveComment(DrupalComment comment) throws DrupalFetchException
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException
    {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            /*JSONObject json = new JSONObject();
            json.append("username", username);
            json.append("password", password);
            data.put("data", json.toString());
            */
            data.put("username", username);
            data.put("password", password);
            ServicesResponse response = requestManager.post(rootPath + "/user/login.json", data);
            // object format should contain sessid, session_name, and user keys.
            validateResponse(response);
            JSONObject object = new JSONObject(response.getResponseBody());
            this.session = object.getString("sessid");
            JSONObject userObject = object.getJSONObject("user");
            DrupalUser user = userObjectSerializer.unserialize(userObject.toString());
            this.currentUser = user;
            return user;
        } catch (IOException e) {
            throw new DrupalLoginException(e);
        } catch (JSONException e) {
            throw new DrupalLoginException(e);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalLoginException(e);
        }
    }

    private void validateResponse(ServicesResponse response) throws DrupalServicesResponseException
    {
        if (response.getStatusCode() >= 400) {
            throw new DrupalServicesResponseException(response.getStatusCode(), response.getReasonPhrase());
        }
    }

    private void assertNoErrors(JSONObject json)
    {
        // TODO: Implement
    }

    public List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int registerNewUser(String username, String password, String email) throws DrupalSaveException
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DrupalComment> getComments(int nid) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DrupalComment> getComments(int nid, int start, int count) throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getFileStream(String filepath) throws IOException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFileDirectoryPath() throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int saveNode(final DrupalNode node) throws DrupalSaveException
    {
        try {

            JsonObject jsonNode = serializeNode(node);
            JSONObject object = new JSONObject();
            object.put("node", new JSONObject(jsonNode.toString()));
            ServicesResponse response;
            if (node.getNid() != 0) {
                response = requestManager.post(rootPath + "/node.json", object.toString());
            } else {
                response = requestManager.put(rootPath + "/node.json", object.toString());
            }
            JSONObject responseObject = new JSONObject(response);
            return responseObject.getInt("nid");
        } catch (IOException e) {
            throw new DrupalSaveException(e);
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        }
    }

    private JsonObject serializeNode(final DrupalNode node)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new UnixTimeDateAdapter());
        builder.registerTypeAdapter(boolean.class, new BooleanAdapter());
        ExclusionStrategy strategy = new ExclusionStrategy()
        {
            public boolean shouldSkipField(FieldAttributes f)
            {
                if ("nid".equals(f.getName())) {
                    // TODO: since we need access to the actual node object to make this check, this is set up as an inline declaration.  Need to figure out how to do this when constructing the serializer.
                    if (node.getNid() == 0) {
                        return true;
                    }
                }
                return "fields".equals(f.getName());
            }

            public boolean shouldSkipClass(Class<?> clazz)
            {
                return false;
            }
        };
        builder.setExclusionStrategies(strategy);
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = builder.create();
        JsonObject jsonNode = (JsonObject)gson.toJsonTree(node);
        Map<String, DrupalField> fields = node.getFields();
        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();
        if (fields != null && fields.size() > 0) {
            for (Map.Entry<String, DrupalField> entry : fields.entrySet()) {
                String name = entry.getKey();
                JsonObject fieldObject = new JsonObject();
                ArrayList<HashMap<String, String>> values = entry.getValue().getValues();
                for (int i = 0; i < values.size(); i++) {
                    HashMap<String, String> map = values.get(i);
                    JsonObject valueObject = new JsonObject();
                    for (Map.Entry<String, String> valueEntry : map.entrySet()) {
                        if (valueEntry.getValue().startsWith("{")) {
                            Map<String, String> element = gson.fromJson(valueEntry.getValue(), type);
                            valueObject.add(valueEntry.getKey(), gson.toJsonTree(element));
                        }
                        else {
                            valueObject.addProperty(valueEntry.getKey(), valueEntry.getValue());
                        }
                    }
                    fieldObject.add(String.valueOf(i), valueObject);
                }
                if (name != null) {
                    jsonNode.add(name, fieldObject);
                }
            }
        }
        return jsonNode;
    }

    public DrupalUser getUser(int uid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.getString(rootPath + "/user/" + uid + ".json");
            // TODO: handle error condition
            return userObjectSerializer.unserialize(response.getResponseBody());
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalUser getCurrentUser() {
        return this.currentUser;
    }

    public List<GenericCookie> getCurrentUserCookie()
    {
        return requestManager.getCookies();
    }

    public String getFileUploadToken() throws DrupalFetchException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DrupalFile saveFileStream(InputStream inputStream, String fileName, String token) throws DrupalSaveException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void initializeSavedState(DrupalSiteContextInstanceState state)
    {
        this.currentUser = state.getUser();
        this.session = state.getSession();
        this.requestManager.initializeSavedState(state);
    }
}
