package org.workhabit.drupal.api;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.workhabit.drupal.api.site.RequestSigningInterceptor;
import org.workhabit.drupal.http.JsonRequestManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:20:31 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class CommonsHttpClientJsonRequestManager implements JsonRequestManager {
    HttpClient client;
    private RequestSigningInterceptor requestSigningInterceptor;

    public CommonsHttpClientJsonRequestManager() {
        client = new HttpClient();
        client.setHttpConnectionManager(new SimpleHttpConnectionManager());
    }

    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException {
        PostMethod postMethod = new PostMethod(path);
        postMethod.setParameter("method", "\"" + method + "\"");
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (escapeInput) {
                    postMethod.setParameter(entry.getKey(), "\"" + entry.getValue() + "\"");
                } else {
                    postMethod.setParameter(entry.getKey(), "" + entry.getValue());
                }
            }
        }
        Header contentTypeHeader = new Header("Content-Type", "application/x-www-form-urlencoded");
        postMethod.setRequestHeader(contentTypeHeader);
        client.executeMethod(postMethod);
        return postMethod.getResponseBodyAsString();
    }

    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data, escapeInput);
    }

    public InputStream get(String path) throws IOException {
        HttpMethod getMethod = new GetMethod(path);
        client.executeMethod(getMethod);
        return getMethod.getResponseBodyAsStream();
    }

    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor) {
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

}
