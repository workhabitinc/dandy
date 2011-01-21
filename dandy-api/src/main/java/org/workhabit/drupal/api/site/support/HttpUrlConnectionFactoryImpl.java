package org.workhabit.drupal.api.site.support;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 1/20/11, 1:55 PM
 */
public class HttpUrlConnectionFactoryImpl implements HttpUrlConnectionFactory {
    public HttpURLConnection getConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        return conn;
    }
}
