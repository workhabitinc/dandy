package org.workhabit.drupal.http;

import android.util.Log;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BufferedHttpEntity;
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
import org.workhabit.drupal.api.site.RequestSigningInterceptor;
import org.workhabit.drupal.api.site.impl.DrupalSiteContextInstanceState;
import org.workhabit.drupal.api.site.support.GenericCookie;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 5, 2010, 6:50:54 PM
 */
@SuppressWarnings({"WeakerAccess", "UnusedDeclaration"})
public class AndroidDrupalServicesRequestManagerImpl implements DrupalServicesRequestManager
{
    private final HttpClient client;
    private RequestSigningInterceptor requestSigningInterceptor;
    private ArrayList<GenericCookie> cookies;
    private BasicCookieStore cookieStore;
    private HttpContext httpContext;

    public AndroidDrupalServicesRequestManagerImpl()
    {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        client = new DefaultHttpClient(cm, params);
    }

    /**
     * Setter set the interceptor to use to sign requests.  Currently only {@link org.workhabit.drupal.api.site.impl.v2.KeyRequestSigningInterceptorImpl}
     * is supported, though OAuth will be available with Drupal Services 3.x.
     *
     * @param requestSigningInterceptor
     */
    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor)
    {
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

    /**
     * Decorator for {@link #post(String, String, java.util.Map, boolean)} that runs the data and method through
     * the specified request signing interceptor.
     *
     * @param path        path, including http:// to the remote request (excludes query string)
     * @param method      the method required by Drupal to execute on services (e.g. node.getStream). See constants on
     *                    {@link org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl} for examples of available methods.
     * @param data        map of key/value pairs corresponding to query string parameters.
     * @param escapeInput true if the method's query string parameters should be quoted.  Typically this is the case
     *                    for Drupal JSON services, but some require that they're not (e.g. comment.save).
     * @return a string response from the web service request.
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeyException
     */
    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws NoSuchAlgorithmException, IOException, InvalidKeyException
    {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data, escapeInput);
    }

    /**
     * make a POST request to the remote web service.  Map values are passed in as key/value pairs (parameters) to the
     * POST request.
     *
     * @param path        path, including http:// to the remote request (excludes query string)
     * @param method      the method required by Drupal to execute on services (e.g. node.getStream). See constants on
     *                    {@link org.workhabit.drupal.api.site.impl.v2.DrupalSiteContextV2Impl} for examples of available methods.
     * @param data        map of key value pairs corresponding to query string parameters
     * @param escapeInput true if the method's query string parameters should be quoted.
     *                    typically this is the case for Drupal JSON services, but some require that they're not.
     * @return a string response from the web service request.
     * @throws IOException
     */
    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException
    {
        HttpPost httpPost = new HttpPost(path);

        List<NameValuePair> parameters = processParameters(method, data, escapeInput);
        Header contentTypeHeader = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader(contentTypeHeader);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        HttpResponse response = client.execute(httpPost, httpContext);
        InputStream contentInputStream = response.getEntity().getContent();
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentInputStream));
        StringWriter sw = new StringWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            sw.write(line);
            sw.write("\n");
        }
        return sw.toString();

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
    public String getString(String path) throws IOException
    {
        InputStream contentInputStream = getStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentInputStream));
        StringWriter sw = new StringWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            sw.write(line);
            sw.write("\n");
        }
        return sw.toString();
    }

    @Override
    public ArrayList<GenericCookie> getCookies()
    {
        return cookies;
    }

    @Override
    public String postFile(String path, String fieldName, InputStream inputStream, String fileName) throws IOException
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
        return sw.toString();
    }

    /**
     * Handles processing of parameters.  This implementation handles processing of parameters so that they are
     * properly interpreted by Drupal's json_server implementation.  Particularly, non-object values must be quoted.
     *
     * @param method      The web service method to invoke
     * @param data        a name/value pair of parameters to pass to the remote service
     * @param escapeInput true if the data should be escaped; false otherwise.
     * @return a list of {@link NameValuePair} mappings to pass to the request entity.
     */
    protected List<NameValuePair> processParameters(String method, Map<String, Object> data, boolean escapeInput)
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        NameValuePair pair = new BasicNameValuePair("method", "\"" + method + "\"");
        parameters.add(pair);
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (escapeInput) {
                    pair = new BasicNameValuePair(entry.getKey(), "\"" + entry.getValue() + "\"");
                }
                else {
                    pair = new BasicNameValuePair(entry.getKey(), "" + entry.getValue());
                }
                parameters.add(pair);
            }
        }
        if (Log.isLoggable("request", Log.DEBUG)) {
            StringBuffer paramString = new StringBuffer();
            for (NameValuePair parameter : parameters) {
                paramString.append(pair.getName()).append("=").append(parameter.getValue()).append("&");
            }
            Log.d("request", "parameter string: " + paramString.toString());
        }
        return parameters;
    }

    public void initializeSavedState(DrupalSiteContextInstanceState state)
    {
        ArrayList<GenericCookie> cookieList = state.getCookies();
        cookies = cookieList;
        for (GenericCookie genericCookie : cookieList) {
            BasicClientCookie cookie = new BasicClientCookie(genericCookie.getName(), genericCookie.getValue());
            cookie.setComment(genericCookie.getComment());
            cookie.setDomain(genericCookie.getDomain());
            cookie.setExpiryDate(genericCookie.getExpiryDate());
            cookie.setPath(genericCookie.getPath());
            cookie.setSecure(genericCookie.isSecure());
            cookie.setValue(genericCookie.getValue());
            cookie.setVersion(genericCookie.getVersion());
            cookieStore.addCookie(cookie);
        }
    }
}
