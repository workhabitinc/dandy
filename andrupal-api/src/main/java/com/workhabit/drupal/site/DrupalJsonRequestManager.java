package com.workhabit.drupal.site;

import org.apache.http.NameValuePair;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 5:43:02 PM
 */
public class DrupalJsonRequestManager extends JsonRequestManager {
    private RequestSigningInterceptor requestSigningInterceptor;

    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor) {
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

    public DrupalJsonRequestManager() {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        client = new DefaultHttpClient(cm, params);
    }

    @Override
    protected List<NameValuePair> processParameters(String method, Map<String, Object> data) {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        NameValuePair pair = new BasicNameValuePair("method", "\"" + method + "\"");
        parameters.add(pair);
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                pair = new BasicNameValuePair(entry.getKey(), "\"" + entry.getValue() + "\"");
                parameters.add(pair);
            }
        }
        return parameters;
    }

    public String postSigned(String path, String method, Map<String, Object> data) throws Exception {
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data);
    }
}
