package org.workhabit.drupal.http;

import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:12:37 PM
 */
public interface DrupalServicesRequestManager {
    /**
     * Make a request to the remote site with content type application/json
     *
     * @param path path, including http:// to the remote request (excludes query string)
     * @param data a String representing the data to post.
     *
     * <b>Note:</b> In this implementation the content type is set to application/json, so the data
     * to post should be a json object and not key/value pairs.
     *
     * If you need to post key/value pairs, {@see DrupalServicesRequestManager#post}
     *
     * @return a string representing the response
     * @throws IOException if there's a problem making the request.
     */
    public ServicesResponse post(String path, String data) throws IOException;

    /**
     * make a POST request to the server with content type application/x-www-form-urlencoded
     *
     * @param path path, including http:// to the remote request (may include query string parameters if there is a need)
     * @param data map of key value pairs corresponding to query string parameters
     * @return a string representing the response.
     * @throws IOException if there's a problem making the request.
     */
    public ServicesResponse post(String path, Map<String, Object> data) throws IOException;

    /**
     * Make a PUT request to the server with content type application/x-www-form-urlencoded
     *
     * @param path the path to the request (including querystring if any)
     * @param data the data to post as key/value pairs
     *
     * @return a service response representing the result of the request.
     *
     * @throws IOException
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public ServicesResponse put(String path, Map<String, Object> data) throws IOException;

    /**
     * Make a PUT request to the server with content type application/x-www-form-urlencoded
     *
     * @param path the path to the request (including querystring if any)
     * @param data the data to PUT as a string.
     *
     * * <b>Note:</b> In this implementation the content type is set to application/json, so the data
     * to PUT should be a json object and not key/value pairs.
     *
     * If you need to PUT key/value pairs, {@see DrupalServicesRequestManager#put(String path, Map data)}

     * @return a service response representing the result of the request.
     *
     * @throws IOException if there's a problem making the request
     */
    public ServicesResponse put(String path, String data) throws IOException;

    @SuppressWarnings({"UnusedDeclaration"})
    public ServicesResponse delete(String path) throws IOException;

    /** make a GET request to the remote site for the specified path
     *
     * @param path full URL to the remote server.
     * @return input stream corresponding to the response data.
     *
     * @throws IOException if there was a problem parsing the response.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public InputStream getStream(String path) throws IOException;

    public ServicesResponse getString(String path) throws IOException;

    ArrayList<GenericCookie> getCookies();

    @SuppressWarnings({"UnusedDeclaration"})
    public String postFile(String path, String fieldName, InputStream inputStream, String fileName) throws IOException;

    void initializeSavedState(DrupalSiteContextInstanceState state);
}
