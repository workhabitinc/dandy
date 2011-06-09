package org.workhabit.drupal.api.site.impl.v3;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workhabit.drupal.api.entity.drupal7.*;
import org.workhabit.drupal.api.json.BooleanAdapter;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializerFactory;
import org.workhabit.drupal.api.json.UnixTimeDateAdapter;
import org.workhabit.drupal.api.site.Drupal7SiteContext;
import org.workhabit.drupal.api.site.exceptions.*;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceStateImpl;
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
public class Drupal7SiteContextImpl implements Drupal7SiteContext
{
    private static Logger log = LoggerFactory.getLogger(Drupal7SiteContextImpl.class.getSimpleName());
    private DrupalSiteContextBridge bridge;

    private DrupalServicesRequestManager requestManager;
    private String rootPath;
    private DrupalJsonObjectSerializer<DrupalNode> nodeSerializer;
    private DrupalJsonObjectSerializer<DrupalUser> userObjectSerializer;
    private String session;
    private DrupalUser currentUser;
    private DrupalJsonObjectSerializer<DrupalComment> commentSerializer;
    private DrupalJsonObjectSerializer<DrupalFile> fileSerializer;

    public Drupal7SiteContextImpl(String drupalSiteUrl, String endpoint)
    {
        bridge = new DrupalSiteContextBridge();
        this.rootPath = String.format("%s/%s", drupalSiteUrl, endpoint);
        bridge.setRootPath(this.rootPath);
        nodeSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        userObjectSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalUser.class);
        log.debug("initialized new Drupal Site Context");
        commentSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalComment.class);
        fileSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalFile.class);
    }

    public void setRequestManager(DrupalServicesRequestManager requestManager)
    {
        this.requestManager = requestManager;
        bridge.setRequestManager(requestManager);
    }

    public void logout() throws DrupalLogoutException
    {
        Map<String, Object> data = new HashMap<String, Object>();
        this.currentUser = null;
        this.session = null;
        try {
            bridge.logout(data);
        } catch (IOException e) {
            throw new DrupalLogoutException(e);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalLogoutException(e);
        }
    }


    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException
    {
        //noinspection NullableProblems
        return getNodeView(viewName, null, 0, 0);
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException
    {
        return getNodeView(viewName, viewArguments, 0, 0);
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments, int offset, int limit) throws DrupalFetchException
    {
        try {
            ServicesResponse response = bridge.getNodeView(viewName, viewArguments, offset, limit);
            return nodeSerializer.unserializeList(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = bridge.getNode(nid);
            return nodeSerializer.unserialize(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }


    public DrupalComment getComment(int cid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = bridge.getComment(cid);
            return commentSerializer.unserialize(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }


    public int saveComment(final DrupalComment comment) throws DrupalFetchException
    {
        String commentString = serializeComment(comment);
        ServicesResponse response = bridge.saveComment(commentString);
        try {
            JSONObject responseObject = new JSONObject(response.getResponseBody());
            return responseObject.getInt("nid");
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }

    }

    private String serializeComment(final DrupalComment comment)
    {
        GsonBuilder builder = new GsonBuilder();
        ExclusionStrategy strategy = new ExclusionStrategy()
        {
            public boolean shouldSkipField(FieldAttributes f)
            {
                if ("cid".equals(f.getName())) {
                    if (comment.getCid() == 0) {
                        return true;
                    }
                }
                if ("uid".equals(f.getName())) {
                    if (comment.getUid() == 0) {
                        return true;
                    }
                }
                return false;
            }

            public boolean shouldSkipClass(Class<?> clazz)
            {
                return false;
            }
        };
        builder.setExclusionStrategies(strategy);
        Gson gson = builder.create();
        return gson.toJson(comment);
    }

    public DrupalUser login(String username, String password) throws DrupalLoginException, DrupalFetchException
    {
        try {
            ServicesResponse response = bridge.login(username, password);
            JSONObject object = new JSONObject(response.getResponseBody());
            this.session = object.getString("sessid");
            JSONObject userObject = object.getJSONObject("user");
            DrupalUser user = userObjectSerializer.unserialize(userObject.toString());
            this.currentUser = user;
            return user;
        } catch (JSONException e) {
            throw new DrupalLoginException(e);
        }
    }


    public List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException
    {
        // TODO: Implement
        return null;
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException
    {
        // TODO: Implement
        return null;
    }

    public int registerNewUser(String username, String password, String email) throws DrupalSaveException
    {
        // TODO: Implement
        return 0;
    }

    public List<DrupalComment> getComments(int nid) throws DrupalFetchException
    {
        return getComments(nid, 0, 0);

    }

    public List<DrupalComment> getComments(int nid, int start, int count) throws DrupalFetchException
    {
        try {
            ServicesResponse response = bridge.getComments(nid, start, count);
            return commentSerializer.unserializeList(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public InputStream getFileStream(String filepath) throws IOException
    {
        return requestManager.getStream(filepath);
    }

    public int saveNode(final DrupalNode node) throws DrupalSaveException
    {
        try {
            JsonObject jsonNode = serializeNode(node);
            JSONObject object = new JSONObject();
            object.put("node", new JSONObject(jsonNode.toString()));
            ServicesResponse response;
            response = bridge.saveNode(node, object);
            JSONObject responseObject = new JSONObject(response.getResponseBody());
            return responseObject.getInt("nid");
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        } catch (DrupalFetchException e) {
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
            ServicesResponse response = bridge.getUser(uid);
            return userObjectSerializer.unserialize(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }


    public DrupalUser getCurrentUser()
    {
        return this.currentUser;
    }

    public ArrayList<GenericCookie> getCurrentUserCookie()
    {
        return requestManager.getCookies();
    }

    public DrupalFile saveFileStream(InputStream inputStream, String fileName) throws DrupalSaveException
    {
        try {
            ServicesResponse response = bridge.saveFileStream(inputStream, fileName);
            return fileSerializer.unserialize(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        } catch (DrupalFetchException e) {
            throw new DrupalSaveException(e);
        }
    }

    public void initializeSavedState(DrupalSiteContextInstanceState state)
    {
        this.currentUser = state.getUser();
        this.session = state.getSession();
        this.requestManager.initializeSavedState(state);
    }

    public DrupalSiteContextInstanceState getSavedState()
    {
        DrupalSiteContextInstanceStateImpl state = new DrupalSiteContextInstanceStateImpl();
        state.setUser(getCurrentUser());
        state.setCookies(getCurrentUserCookie());
        state.setSession(session);
        return state;
    }
}
