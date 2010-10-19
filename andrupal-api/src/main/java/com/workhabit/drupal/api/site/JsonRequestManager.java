package com.workhabit.drupal.api.site;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 5, 2010, 6:50:54 PM
 */
public abstract class JsonRequestManager {
    HttpClient client;

    public String post(String path, String method, Map<String, Object> data) throws IOException {
        HttpPost httpPost = new HttpPost(path);

        List<NameValuePair> parameters = processParameters(method, data);

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

    protected abstract List<NameValuePair> processParameters(String method, Map<String, Object> data);
}
