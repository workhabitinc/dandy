package com.workhabit.drupal.api.site;

import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 14, 2010, 12:06:33 PM
 */
public interface RequestSigningInterceptor {
    public void sign(String path, String method, Map<String, Object> data) throws Exception;
}
