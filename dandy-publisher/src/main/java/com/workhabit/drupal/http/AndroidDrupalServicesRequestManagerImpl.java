package com.workhabit.drupal.http;

import android.util.Log;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.workhabit.drupal.api.site.RequestSigningInterceptor;
import org.workhabit.drupal.http.DrupalServicesRequestManager;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 5, 2010, 6:50:54 PM
 */
@SuppressWarnings({"WeakerAccess"})
public class AndroidDrupalServicesRequestManagerImpl implements DrupalServicesRequestManager {
    private HttpClient client;
    private RequestSigningInterceptor requestSigningInterceptor;

    public AndroidDrupalServicesRequestManagerImpl() {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        client = new DefaultHttpClient(cm, params);
    }

    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor) {
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data, escapeInput);
    }

    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException {
        HttpPost httpPost = new HttpPost(path);

        List<NameValuePair> parameters = processParameters(method, data, escapeInput);
        Header contentTypeHeader = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader(contentTypeHeader);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        HttpResponse response = client.execute(httpPost);
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

    public InputStream get(String path) throws IOException {
        HttpGet get = new HttpGet(path);
        HttpResponse response = client.execute(get);
        return new BufferedHttpEntity(response.getEntity()).getContent();
    }

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
        if (Log.isLoggable("request", Log.DEBUG)) {
            StringBuffer paramString = new StringBuffer();
            for (NameValuePair parameter : parameters) {
                paramString.append(pair.getName()).append("=").append(parameter.getValue()).append("&");
            }
            Log.d("request", "parameter string: " + paramString.toString());
        }
        return parameters;
    }
}
