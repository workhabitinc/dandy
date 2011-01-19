package org.workhabit.drupal.api;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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
    HttpClient client;
    private RequestSigningInterceptor requestSigningInterceptor;
    private List<GenericCookie> cookies;

    public CommonsHttpClientDrupalServicesRequestManager() {
        client = new HttpClient();
        client.setHttpConnectionManager(new SimpleHttpConnectionManager());
    }

    public String post(String path, String method, Map<String, Object> data, boolean escapeInput) throws IOException {
        PostMethod postMethod = new PostMethod(path);
        postMethod.setParameter("method", "\"" + method + "\"");
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (escapeInput
                        || "nonce".equals(key)
                        || "hash".equals(key)
                        || "domain_time_stamp".equals(key)
                        || "domain_name".equals(key)
                        || "sessid".equals(key)
                        ) {
                    postMethod.setParameter(key, "\"" + value + "\"");
                } else {
                    postMethod.setParameter(key, "" + value);
                }
            }
        }
        Header contentTypeHeader = new Header("Content-Type", "application/x-www-form-urlencoded");
        postMethod.setRequestHeader(contentTypeHeader);
        client.executeMethod(postMethod);
        Cookie[] clientCookies = client.getState().getCookies();

        cookies = new ArrayList<GenericCookie>();
        for (Cookie cookie : clientCookies) {
            GenericCookie siteCookie = new GenericCookie();
            siteCookie.setComment(cookie.getComment());
            siteCookie.setDomain(cookie.getDomain());
            siteCookie.setExpiryDate(cookie.getExpiryDate());
            siteCookie.setName(cookie.getName());
            siteCookie.setPath(cookie.getPath());
            siteCookie.setSecure(cookie.getSecure());
            siteCookie.setVersion(cookie.getVersion());
            siteCookie.setValue(cookie.getValue());
            cookies.add(siteCookie);
        }
        return postMethod.getResponseBodyAsString();
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
        HttpMethod getMethod = new GetMethod(path);
        client.executeMethod(getMethod);
        return getMethod.getResponseBodyAsStream();
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

}
