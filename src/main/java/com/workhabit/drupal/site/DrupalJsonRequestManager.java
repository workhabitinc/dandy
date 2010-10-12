package com.workhabit.drupal.site;

import android.content.res.Resources;
import com.google.inject.Inject;
import com.workhabit.drupal.R;
import org.apache.http.NameValuePair;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Sep 24, 2010, 5:43:02 PM
 */
public class DrupalJsonRequestManager extends JsonRequestManager {
    private DrupalAuthenticationToken token;

    @Inject
    public DrupalJsonRequestManager(DrupalAuthenticationToken token) {
        this.token = token;

        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

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
        Long timestamp = Calendar.getInstance().getTimeInMillis();
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(Double.toString(System.currentTimeMillis() + Math.random()).getBytes());
        StringBuffer hexString = new StringBuffer();
        byte messageDigest[] = digest.digest();
        for (byte aMessageDigest : messageDigest) {
            hexString.append(Integer.toHexString(0xFF & aMessageDigest));
        }

        String nonce = hexString.toString();
        //();
        String hash = token.generateHmacHash(timestamp, Resources.getSystem().getString(R.string.drupal_domain), nonce, method);

        // add params for hash
        data.put("hash", hash);
        data.put("timestamp", Long.toString(timestamp));
        data.put("nonce", nonce);
        return post(path, method, data);
    }
}
