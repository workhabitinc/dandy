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
import org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextInstanceStateImpl;
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
    private DrupalJsonObjectSerializer<DrupalComment> commentSerializer;

    public DrupalSiteContextV3Impl(String drupalSiteUrl, String endpoint)
    {
        this.drupalSiteUrl = drupalSiteUrl;
        this.endpoint = endpoint;
        this.rootPath = String.format("%s/%s", drupalSiteUrl, endpoint);
        nodeSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
        userObjectSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalUser.class);
        log.debug("initialized new Drupal Site Context");
        commentSerializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalComment.class);
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
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            this.currentUser = null;
            this.session = null;
            ServicesResponse response = requestManager.post(rootPath + "/user/logout.json", data);
            validateResponse(response);
        } catch (IOException e) {
            throw new DrupalLogoutException(e);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalLogoutException(e);
        }
    }

    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException
    {
        return getNodeView(viewName, null, 0, 0);
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException
    {
        return getNodeView(viewName, viewArguments, 0, 0);
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments, int offset, int limit) throws DrupalFetchException
    {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(rootPath).append("/views/").append(viewName).append(".json");
            sb.append("?XDEBUG_SESSION_START=12555");
            if (viewArguments != null && !"".equals(viewArguments)) {
                sb.append("&args=").append(viewArguments);
            }
            sb.append("&offset=").append(offset);
            sb.append("&limit=").append(limit);

            ServicesResponse response = requestManager.getString(sb.toString());
            validateResponse(response);
            return nodeSerializer.unserializeList(response.getResponseBody());
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public DrupalNode getNode(int nid) throws DrupalFetchException
    {
        try {
            ServicesResponse response = requestManager.getString(rootPath + "/node/" + nid + ".json");
            validateResponse(response);
            return nodeSerializer.unserialize(response.getResponseBody());
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (DrupalServicesResponseException e) {
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
        return getComments(nid, 0, 0);

    }

    public List<DrupalComment> getComments(int nid, int start, int count) throws DrupalFetchException
    {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("nid", nid);
            if (start != 0) {
                data.put("start", start);
            }
            if (count != 0) {
                data.put("count", count);
            }
            ServicesResponse response = requestManager.post(rootPath + "/comment/loadNodeComments.json", data);
            validateResponse(response);
            List<DrupalComment> drupalComments = commentSerializer.unserializeList(response.getResponseBody());
            return drupalComments;
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (DrupalServicesResponseException e) {
            throw new DrupalFetchException(e);
        }
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
            if (node.getNid() == 0) {
                response = requestManager.post(rootPath + "/node.json", object.toString());
            }
            else {
                response = requestManager.put(rootPath + "/node/" + node.getNid() + ".json", object.toString());
            }
            validateResponse(response);
                JSONObject responseObject = new JSONObject(response.getResponseBody());
                return responseObject.getInt("nid");

        } catch (IOException e) {
            throw new DrupalSaveException(e);
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        } catch (DrupalServicesResponseException e) {
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
            validateResponse(response);
            return userObjectSerializer.unserialize(response.getResponseBody());
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (DrupalServicesResponseException e) {
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

    public DrupalSiteContextInstanceState getSavedState() {
        DrupalSiteContextInstanceStateImpl state = new DrupalSiteContextInstanceStateImpl();
        state.setUser(getCurrentUser());
        state.setCookies(getCurrentUserCookie());
        state.setSession(session);
        return state;
    }
}
