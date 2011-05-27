package org.workhabit.drupal.api.site.support;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.http.DrupalServicesRequestManager;
import org.workhabit.drupal.http.ServicesResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 5, 2010, 6:50:54 PM
 */
@SuppressWarnings({"WeakerAccess", "UnusedDeclaration"})
public class AndroidDrupalServicesRequestManagerImpl implements DrupalServicesRequestManager
{
    private static final Logger log = LoggerFactory.getLogger(AndroidDrupalServicesRequestManagerImpl.class.getSimpleName());
    private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_SCHEME = "http";
    private static final String HTTPS_SCHEME = "https";
    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;
    private final HttpClient client;
    private ArrayList<GenericCookie> cookies;
    private BasicCookieStore cookieStore;
    private HttpContext httpContext;

    public AndroidDrupalServicesRequestManagerImpl()
    {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        //noinspection deprecation
        schemeRegistry.register(new Scheme(HTTP_SCHEME, PlainSocketFactory.getSocketFactory(), HTTP_PORT));
        //noinspection deprecation
        schemeRegistry.register(new Scheme(HTTPS_SCHEME, PlainSocketFactory.getSocketFactory(), HTTPS_PORT));
        @SuppressWarnings({"deprecation"}) ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        client = new DefaultHttpClient(cm, params);
    }

    /**
     * make a POST request to the remote web service.  Map values are passed in as key/value pairs (parameters) to the
     * POST request.
     *
     *
     * @param path path, including http:// to the remote request (excludes query string)
     * @param data map of key value pairs corresponding to query string parameters
     * @return a string response from the web service request.
     * @throws IOException
     */
    public ServicesResponse post(String path, Map<String, Object> data) throws IOException
    {
        HttpPost post = new HttpPost(path);
        List<NameValuePair> parameters = processParameters(data);
        Header contentTypeHeader = new BasicHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_FORM_URLENCODED);
        post.setHeader(contentTypeHeader);
        post.setEntity(new UrlEncodedFormEntity(parameters));
        return executeMethod(post);
    }

    /**
     * Stores cookies from the request in an internal format that's agnostic to the underlying http implementation.
     */
    private void processCookies()
    {
        cookies = new ArrayList<GenericCookie>();
        List<Cookie> cookieList = cookieStore.getCookies();
        for (Cookie cookie : cookieList) {
            GenericCookie siteCookie = new GenericCookie();
            siteCookie.setComment(cookie.getComment());
            siteCookie.setDomain(cookie.getDomain());
            siteCookie.setExpiryDate(cookie.getExpiryDate());
            siteCookie.setName(cookie.getName());
            siteCookie.setPath(cookie.getPath());
            siteCookie.setSecure(cookie.isSecure());
            siteCookie.setVersion(cookie.getVersion());
            siteCookie.setValue(cookie.getValue());
            cookies.add(siteCookie);
        }
    }

    /**
     * Make an HTTP post to the remote service.
     *
     * @param path path, including http:// to the remote request (excludes query string)
     * @param data a String representing the data to post.
     *
     * <b>Note:</b> In this implementation the content type is set to application/json, so the data
     * to post should be a json object and not key/value pairs.
     *
     * If you need to post key/value pairs, {@see DrupalServicesRequestManager#post}
     *
     * @return
     * @throws IOException
     */
    public ServicesResponse post(String path, String data) throws IOException
    {
        HttpPost post = new HttpPost(path);
        post.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        post.setEntity(new ByteArrayEntity(data.getBytes()));
        return executeMethod(post);

    }

    /**
     * Execute the method.  This implementation is private since requesting instances should not be invoking it
     * directly.  Use one of get, post, put, or delete instead.
     *
     * @param method the request method to invoke
     * @return a ServicesResponse object containing the result of the request.
     * @throws IOException if there's an error requesting data from the backend.
     */
    private ServicesResponse executeMethod(HttpUriRequest method) throws IOException
    {
        log.debug(method.getMethod() + " : " + method.getURI().toURL().toString());
        HttpResponse response = client.execute(method, httpContext);
        ServicesResponse servicesResponse = new ServicesResponse();
        servicesResponse.setReasonPhrase(response.getStatusLine().getReasonPhrase());
        servicesResponse.setStatusCode(response.getStatusLine().getStatusCode());
        InputStream contentInputStream = response.getEntity().getContent();
        processCookies();
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentInputStream));
        StringWriter sw = new StringWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            sw.write(line);
            sw.write("\n");
        }
        servicesResponse.setResponseBody(sw.toString());
        log.debug("RESPONSE: " + sw.toString());
        return servicesResponse;
    }

    /**
     * Perform an HTTP PUT request with key/value pairs as data.
     *
     * @param path the path to the request (including querystring if any)
     * @param data the data to post as key/value pairs
     *
     * @return the servicesResponse with the result of the request.
     * @throws IOException if there's an error with the request.
     */
    public ServicesResponse put(String path, Map<String, Object> data) throws IOException
    {
        HttpPut put = new HttpPut(path);
        put.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_FORM_URLENCODED);
        List<NameValuePair> parameters = processParameters(data);
        put.setEntity(new UrlEncodedFormEntity(parameters));
        return executeMethod(put);
    }

    /**
     * Perform an HTTP PUT request with raw string as data (e.g. a JSON string)
     *
     * * <b>Note:</b> In this implementation the content type is set to application/json, so the data
     * to PUT should be a json object and not key/value pairs.
     *
     * If you need to PUT key/value pairs, {@see DrupalServicesRequestManager#put(String path, Map data)}
     *
     * @param path the path to the request (including querystring if any)
     * @param data the data to PUT as a string.
     *
     * @return a servicesResponse object with the result of the request.
     * @throws IOException
     */
    public ServicesResponse put(String path, String data) throws IOException
    {
        HttpPut put = new HttpPut(path);
        put.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        put.setEntity(new ByteArrayEntity(data.getBytes()));
        log.debug(data);
        return executeMethod(put);
    }

    /**
     * Perform an HTTP DELETE request.
     * @param path the path to invoke
     * @return a servicesResponse object with the result of the request.
     * @throws IOException
     */
    public ServicesResponse delete(String path) throws IOException
    {
        HttpDelete delete = new HttpDelete(path);
        return executeMethod(delete);
    }

    /**
     * Makes an HTTP GET request to the specified path, returning an inputstream.  Use this method as opposed to
     * {@link #getString(String)} if you expect a large response (e.g. a video or image).
     *
     * @param path full URL to the remote server.
     * @return a buffered input stream representing the response data.
     * @throws IOException if there's an error during the request.
     */
    public InputStream getStream(String path) throws IOException
    {
        HttpGet get = new HttpGet(path);
        HttpResponse response = client.execute(get, httpContext);
        return new BufferedHttpEntity(response.getEntity()).getContent();
    }

    /**
     * Makes an HTTP GET request to the specified path, returning a string.  Consider using {@link #getStream(String)}
     * if the size of the response is large or unknown.  This will prevent the response from being stored in memory.
     *
     * @param path full URL to the remote server.
     * @return a string representing the response data.
     * @throws IOException if there's an error during the request.
     */
    public ServicesResponse getString(String path) throws IOException
    {
        HttpGet get = new HttpGet(path);
        return executeMethod(get);
    }

    /**
     * Get a list of cookies associated with this request manager.  Cookies should represent the latest state of cookies
     * as of the most recent request.
     *
     * @return a list of cookies.
     */
    public ArrayList<GenericCookie> getCookies()
    {
        return cookies;
    }

    /**
     * Perform an HTTP POST with a file in multipart form.
     *
     *
     * @param path full URL to the remote server.
     * @param fieldName the field name to post the file as
     * @param inputStream inputstream to the file. For efficiency reasons, it's recommended to NOT use a
     * {@link java.io.ByteArrayInputStream} for large files.
     *
     * @param fileName the file name to post
     * @return a servicesResponse object with the result of the request
     * @throws IOException
     */
    public ServicesResponse postFile(String path, String fieldName, InputStream inputStream, String fileName) throws IOException
    {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart(new FormBodyPart(fieldName, new InputStreamBody(new BufferedInputStream(inputStream), fileName)));
        HttpPost httpPost = new HttpPost(path);
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost, httpContext);
        InputStream contentInputStream = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentInputStream));
        StringWriter sw = new StringWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            sw.write(line);
            sw.write("\n");
        }
        ServicesResponse servicesResponse = new ServicesResponse();
        servicesResponse.setReasonPhrase(response.getStatusLine().getReasonPhrase());
        servicesResponse.setStatusCode(response.getStatusLine().getStatusCode());
        servicesResponse.setResponseBody(sw.toString());
        return servicesResponse;
    }

    /**
     * Handles processing of parameters.  This implementation handles processing of parameters so that they are
     * properly interpreted by Drupal's json_server implementation.  Particularly, non-object values must be quoted.
     *
     * @param data a name/value pair of parameters to pass to the remote service
     * @return a list of {@link NameValuePair} mappings to pass to the request entity.
     */
    protected List<NameValuePair> processParameters(Map<String, Object> data)
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (data != null && data.size() > 0) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                parameters.add(pair);
            }
        }

        if (log.isDebugEnabled()) {
            StringBuilder paramString = new StringBuilder();
            boolean first = true;
            for (NameValuePair parameter : parameters) {
                if (first) {
                    first = false;
                }
                else {
                    paramString.append("&");
                }
                paramString.append(parameter.getName()).append("=").append(parameter.getValue());
            }
            log.debug("parameter string: " + paramString.toString());
        }
        return parameters;
    }

    public void initializeSavedState(DrupalSiteContextInstanceState drupalSiteContextInstanceState)
    {
        cookies = drupalSiteContextInstanceState.getCookies();
        for (GenericCookie genericCookie : cookies) {
            cookieStore.clear();
            BasicClientCookie cookie = new BasicClientCookie(genericCookie.getName(), genericCookie.getValue());
            cookie.setComment(genericCookie.getComment());
            cookie.setDomain(genericCookie.getDomain());
            cookie.setExpiryDate(genericCookie.getExpiryDate());
            cookie.setPath(genericCookie.getPath());
            cookie.setSecure(genericCookie.isSecure());
            cookie.setVersion(genericCookie.getVersion());
            cookieStore.addCookie(cookie);
        }
    }
}
