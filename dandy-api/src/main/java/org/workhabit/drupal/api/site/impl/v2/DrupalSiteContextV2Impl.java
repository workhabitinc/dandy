package org.workhabit.drupal.api.site.impl.v2;

import com.google.gson.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.workhabit.drupal.api.entity.*;
import org.workhabit.drupal.api.json.BooleanAdapter;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializer;
import org.workhabit.drupal.api.json.DrupalJsonObjectSerializerFactory;
import org.workhabit.drupal.api.site.DrupalSiteContext;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;
import org.workhabit.drupal.api.site.exceptions.DrupalLoginException;
import org.workhabit.drupal.api.site.exceptions.DrupalLogoutException;
import org.workhabit.drupal.api.site.exceptions.DrupalSaveException;
import org.workhabit.drupal.api.site.support.Base64;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 12:21:03 PM
 */
public class DrupalSiteContextV2Impl implements DrupalSiteContext {

    private static final String JSON_SERVICE_PATH = "/services/json";
    private static final String SERVICE_NAME_COMMENT_LOAD = "comment.load";
    private static final String SERVICE_NAME_COMMENT_SAVE = "comment.save";
    private static final String SERVICE_NAME_USER_LOGIN = "user.login";
    private static final String SERVICE_NAME_VIEWS_GET = "views.get";
    private static final String SERVICE_NAME_TAXONOMY_DICTIONARY = "taxonomy.dictionary";
    private static final String SERVICE_NAME_FILE_SAVE = "file.save";
    private static final String SERVICE_NAME_FILE_GETDIRECTORYPATH = "file.getDirectoryPath";
    private static final String SERVICE_NAME_COMMENT_LOADNODECOMMENTS = "comment.loadNodeComments";
    private static final String SERVICE_NAME_NODE_SAVE = "node.save";
    private DrupalServicesRequestManager drupalServicesRequestManager;

    private String session;

    private DrupalUser user;
    private final String drupalSiteUrl;
    private final String servicePath;

    /**
     * Constructor takes an authentication token to use for the lifecycle of requests for this instance
     *
     * @param drupalSiteUrl site url to connect to
     */
    public DrupalSiteContextV2Impl(String drupalSiteUrl) {
        this.drupalSiteUrl = drupalSiteUrl;
        this.servicePath = new StringBuilder().append(drupalSiteUrl).append(JSON_SERVICE_PATH).toString();
    }

    /**
     * call system.connect on the current instance.  This is required for key authentication to work properly.
     */
    public void connect() throws DrupalFetchException {
        try {
            String result = drupalServicesRequestManager.post(servicePath, "system.connect", null, true);
            JSONObject object = new JSONObject(result);
            if ("true".equals(object.getString("#error"))) {
                throw new DrupalFetchException(object);
            }
            if (object.has("#data")) {
                try {
                    JSONObject data = object.getJSONObject("#data");
                    if (data != null) {
                        String sessid = data.getString("sessid");
                        if (sessid != null) {
                            setSession(sessid);
                        }

                    }
                } catch (Exception e) {
                    // no sessid returned
                }
            }
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    /**
     * Logs out the current user.  The user's session id is cleared, and Drupal is notified of the logout event.
     *
     * @throws DrupalLogoutException if there is a problem logging out the current user.  This can happen,
     *                               for example, if the user is already logged out.
     */
    public void logout() throws DrupalLogoutException {
        try {
            connect();
            String result = drupalServicesRequestManager.postSigned(servicePath, "user.logout", null, true);
            JSONObject object = new JSONObject(result);
            if ("true".equals(object.getString("#error"))) {
                throw new DrupalFetchException(object);
            }
            setSession(null);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalLogoutException(e);
        } catch (IOException e) {
            throw new DrupalLogoutException(e);
        } catch (JSONException e) {
            throw new DrupalLogoutException(e);
        } catch (DrupalFetchException e) {
            throw new DrupalLogoutException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalLogoutException(e);
        }

    }

    /**
     * @param viewName the name of the view to return.  This is an override for {@link #getNodeView(String, String)}
     *                 for views that don't take any arguments.
     * @return list of drupal nodes corresponding to the result of the view.
     * @throws DrupalFetchException
     */
    public List<DrupalNode> getNodeView(String viewName) throws DrupalFetchException {
        return getNodeView(viewName, null);
    }

    /**
     * Helper function to check for error conditions in the returned JSON object.
     *
     * @param objectResult the JSONObject to check for errors.  The structure of this object is generally:
     *                     <p/>
     *                     <pre>
     *                                                                                 {
     *                                                                                   '#error': boolean
     *                                                                                   '#data': 'json string containing the result or error string if #error is true.'
     *                                                                                 }
     *                                                                                 </pre>
     * @throws JSONException        if there's an error deserializing the response.
     * @throws DrupalFetchException if an error occurred. The message of the exception contains the error.
     *                              See {@link org.workhabit.drupal.api.site.exceptions.DrupalFetchException#getMessage()}
     */
    protected void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult);
        }
    }

    /**
     * Fetches a Drupal Node by Node ID.
     *
     * @param nid the ID of the drupal node to fetch
     * @return Drupal Node if there's a match. Null otherwise.
     * @throws DrupalFetchException if there's an error fetching the node from Drupal, or if there's a
     *                              serialization problem.
     */
    public DrupalNode getNode(int nid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        data.put("sessid", session);
        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, "node.get", data, true);
            DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
            return serializer.unserialize(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    /**
     * Fetches an individual comment from Drupal based on CID
     *
     * @param cid the id of the comment to fetch
     * @return a DrupalComment object representing the comment data, null otherwise.
     * @throws DrupalFetchException if there's a problem fetching the comment.
     */
    public DrupalComment getComment(int cid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("cid", cid);
        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }

        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_COMMENT_LOAD, data, true);
            DrupalJsonObjectSerializer<DrupalComment> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalComment.class);
            return serializer.unserialize(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public void saveComment(final DrupalComment comment) throws DrupalFetchException {
        connect();
        GsonBuilder builder = new GsonBuilder();
        ExclusionStrategy strategy = new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes f) {
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

            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        builder.setExclusionStrategies(strategy);
        Gson gson = builder.create();
        String jsonComment = gson.toJson(comment);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("comment", jsonComment);
        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }

        try {
            String response = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_COMMENT_SAVE, data, false);
            System.out.println(response);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalSaveException(e);
        } catch (IOException e) {
            throw new DrupalSaveException(e);
        } catch (InvalidKeyException e) {
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

        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }

        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_USER_LOGIN, data, true);
            return processLoginResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getTermView(String viewName) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }

        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_VIEWS_GET, data, true);
            return processGetTermViewResult(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalTaxonomyTerm> getCategoryList() throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }

        data.put("vid", 1);
        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_TAXONOMY_DICTIONARY, data, true);
            return processGetTermViewResult(result);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalFetchException(e);
        }
    }

    public void registerNewUser(String username, String password, String email) {
        // TODO: Implement

    }

    public int saveFile(byte[] bytes, String fileName) throws DrupalFetchException {
        connect();

        try {
            String filePath = getFileDirectoryPath();
            Map<String, Object> data = new HashMap<String, Object>();
            DrupalFile file = new DrupalFile();
            file.setFile(Base64.encodeBytes(bytes));
            file.setFilename(fileName);
            file.setFilepath(String.format("%s/%s", filePath, fileName));
            DrupalJsonObjectSerializer<DrupalFile> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalFile.class);

            data.put("file", serializer.serialize(file));
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_FILE_SAVE, data, false);
            JSONObject object = new JSONObject(result);
            // TODO: handle error case
            return object.getInt("#data");
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalFetchException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public String getFileDirectoryPath() throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("sessid", session);
        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_FILE_GETDIRECTORYPATH, data, true);
            JSONObject objectResult = new JSONObject(result);
            assertNoErrors(objectResult);
            return objectResult.getString("#data");
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalFetchException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalFetchException(e);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        }
    }

    public List<DrupalComment> getComments(int nid) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nid", nid);
        // TODO: Parameterize these
        data.put("count", 0);
        data.put("start", 0);

        data.put("sessid", session);
        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_COMMENT_LOADNODECOMMENTS, data, true);
            JSONObject objectResult = new JSONObject(result);
            assertNoErrors(objectResult);
            DrupalJsonObjectSerializer<DrupalComment> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalComment.class);
            return serializer.unserializeList(result);
        } catch (IOException e) {
            throw new DrupalFetchException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalFetchException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalFetchException(e);
        } catch (JSONException e) {
            throw new DrupalFetchException(e);
        }
    }

    public InputStream getFileStream(String filepath) throws IOException {
        return drupalServicesRequestManager.get(drupalSiteUrl + "/" + filepath);
    }

    public List<DrupalNode> getNodeView(String viewName, String viewArguments) throws DrupalFetchException {
        connect();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("view_name", viewName);
        if (viewArguments != null) {
            data.put("args", viewArguments);
        }
        if (session != null && !"".equals(session)) {
            data.put("sessid", session);
        }
        try {
            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_VIEWS_GET, data, true);
            DrupalJsonObjectSerializer<DrupalNode> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalNode.class);
            return serializer.unserializeList(result);
        } catch (Exception e) {
            throw new DrupalFetchException(e);
        }
    }

    private List<DrupalTaxonomyTerm> processGetTermViewResult(String result) throws DrupalFetchException, JSONException {
        DrupalJsonObjectSerializer<DrupalTaxonomyTerm> serializer = DrupalJsonObjectSerializerFactory.getInstance(DrupalTaxonomyTerm.class);
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

    public int saveNode(final DrupalNode node) throws DrupalSaveException {
        try {
            connect();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(boolean.class, new BooleanAdapter());
            ExclusionStrategy strategy = new ExclusionStrategy() {
                public boolean shouldSkipField(FieldAttributes f) {
                    if ("nid".equals(f.getName())) {
                        if (node.getNid() == 0) {
                            return true;
                        }
                    }
                    return false;
                }

                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            };

            builder.setExclusionStrategies(strategy);
            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Gson gson = builder.create();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("node", gson.toJson(node));
            data.put("sessid", session);

            String result = drupalServicesRequestManager.postSigned(servicePath, SERVICE_NAME_NODE_SAVE, data, false);
            JSONObject objectResult = new JSONObject(result);
            assertNoErrors(objectResult);
            return objectResult.getInt("#data");
        } catch (IOException e) {
            throw new DrupalSaveException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DrupalSaveException(e);
        } catch (InvalidKeyException e) {
            throw new DrupalSaveException(e);
        } catch (DrupalFetchException e) {
            throw new DrupalSaveException(e);
        } catch (JSONException e) {
            throw new DrupalSaveException(e);
        }
    }

    void setSession(String session) {
        this.session = session;
    }

    public void setDrupalServicesRequestManager(DrupalServicesRequestManager drupalServicesRequestManager) {
        this.drupalServicesRequestManager = drupalServicesRequestManager;
    }
}
