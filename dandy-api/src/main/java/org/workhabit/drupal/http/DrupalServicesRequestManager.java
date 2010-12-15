package org.workhabit.drupal.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * {@link org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl} for examples of available methods.
     *
     * @param data map of key value pairs corresponding to query string parameters
     * @param escapeInput true if the method's query string parameters should be quoted.
     * typically this is the case for Drupal JSON services, but some require that they're not.
     *
     * @return a string representing the response
     *
     * @throws IOException if there's a problem making the request.
     */
    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException;

    /**
     * make a POSt request to the remote site after signing the request.
     * @param path path, including http:// to the remote request (excludes query string)
     * @param method the method required by Drupal to execute on services (e.g. node.getStream). See constants on
     * {@link org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl} for examples of available methods.
     *
     * @param data map of key/value pairs corresponding to query string parameters.
     *
     * @param escapeInput true if the method's query string parameters should be quoted.  Typically this is the case
     * for Drupal JSON services, but some require that they're not (e.g. comment.save).
     *
     * @return a string representing the response
     *
     * @throws IOException if there's a problem making the request.
     * @throws NoSuchAlgorithmException if an invalid algorithm was specified as input to the request interceptor.
     * @throws InvalidKeyException if an invalid key was specified as input to the request interceptor.
     */
    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException, NoSuchAlgorithmException, InvalidKeyException;

    /** make a GET request to the remote site for the specified path
     *
     * @param path full URL to the remote server.
     * @return input stream corresponding to the response data.
     *
     * @throws IOException if there was a problem parsing the response.
     */
    public InputStream getStream(String path) throws IOException;

    public String getString(String path) throws IOException;
}
