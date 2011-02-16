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
     * Make a request to the remote site without signing.
     * @param path path, including http:// to the remote request (excludes query string)
     * @param method the method required by Drupal to execute on services (e.g. node.getStream). See constants on
     * {@link org.workhabit.drupal.api.site.impl.v3.DrupalSiteContextV3Impl} for examples of available methods.
     *
     * @param data map of key value pairs corresponding to query string parameters
     * @param escapeInput true if the method's query string parameters should be quoted.
     * typically this is the case for Drupal JSON services, but some require that they're not.
     *
     * @return a string representing the response
     *
     * @throws IOException if there's a problem making the request.
     */
    public ServicesResponse post(String path, String data) throws IOException;
    public ServicesResponse post(String path, Map<String, Object> data) throws IOException;

    // used for update
    public ServicesResponse put(String path, Map<String, Object> data) throws IOException;
    public ServicesResponse put(String path, String data) throws IOException;

    public ServicesResponse delete(String path) throws IOException;

    /** make a GET request to the remote site for the specified path
     *
     * @param path full URL to the remote server.
     * @return input stream corresponding to the response data.
     *
     * @throws IOException if there was a problem parsing the response.
     */
    public InputStream getStream(String path) throws IOException;

    public ServicesResponse getString(String path) throws IOException;

    ArrayList<GenericCookie> getCookies();

    public String postFile(String path, String fieldName, InputStream inputStream, String fileName) throws IOException;

    void initializeSavedState(DrupalSiteContextInstanceState state);
}
