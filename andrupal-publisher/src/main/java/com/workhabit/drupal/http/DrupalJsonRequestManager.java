package com.workhabit.drupal.http;

import org.workhabit.drupal.api.site.RequestSigningInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 5:43:02 PM
 */
public class DrupalJsonRequestManager extends AndroidJsonRequestManagerImpl {
    private RequestSigningInterceptor requestSigningInterceptor;

    public void setRequestSigningInterceptor(RequestSigningInterceptor requestSigningInterceptor) {
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

    @Override
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

    public String postSigned(String path, String method, Map<String, Object> data, boolean escapeInput) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        if (requestSigningInterceptor != null) {
            requestSigningInterceptor.sign(path, method, data);
        }
        return post(path, method, data, escapeInput);
    }
}
