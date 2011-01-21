package org.workhabit.drupal.api.site.support;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 1/20/11, 1:57 PM
 */
public interface HttpUrlConnectionFactory {
    public HttpURLConnection getConnection(String url) throws IOException;
}
