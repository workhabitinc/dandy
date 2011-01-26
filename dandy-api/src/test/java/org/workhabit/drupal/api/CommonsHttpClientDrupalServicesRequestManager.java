package org.workhabit.drupal.api;

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
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.workhabit.drupal.api.site.RequestSigningInterceptor;
import org.workhabit.drupal.api.site.support.GenericCookie;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:20:31 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class CommonsHttpClientDrupalServicesRequestManager implements DrupalServicesRequestManager {
    private final HttpClient client;
    private RequestSigningInterceptor requestSigningInterceptor;
    private ArrayList<GenericCookie> cookies;
    private BasicCookieStore cookieStore;
    private HttpContext httpContext;

    public CommonsHttpClientDrupalServicesRequestManager() {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, PlainSocketFactory.getSocketFactory()));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        client = new DefaultHttpClient(cm, params);
    }

    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException {
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

    public String postFile(String path, String fieldName, InputStream inputStream, String fileName) throws IOException {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart(new FormBodyPart(fieldName, new InputStreamBody(inputStream, fileName)));
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


    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data, escapeInput);
    }

    public InputStream getStream(String path) throws IOException {
        HttpGet get = new HttpGet(path);
        HttpResponse response = client.execute(get, httpContext);
        return new BufferedHttpEntity(response.getEntity()).getContent();
    }

    public String getString(String path) throws IOException {
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

    public List<GenericCookie> getCookies() {
        return cookies;
    }

    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor) {
        this.requestSigningInterceptor = requestSigningInterceptor;
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
    protected List<NameValuePair> processParameters(String method, Map<String, Object> data, boolean escapeInput) {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        NameValuePair pair = new BasicNameValuePair("method", "\"" + method + "\"");
        parameters.add(pair);
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (escapeInput) {
                    pair = new BasicNameValuePair(entry.getKey(), "\"" + entry.getValue() + "\"");
                } else {
                    pair = new BasicNameValuePair(entry.getKey(), "" + entry.getValue());
                }
                parameters.add(pair);
            }
        }
        return parameters;
    }

}
