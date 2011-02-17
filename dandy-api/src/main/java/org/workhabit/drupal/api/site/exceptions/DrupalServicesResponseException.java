package org.workhabit.drupal.api.site.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/15/11, 2:42 PM
 */
public class DrupalServicesResponseException extends Exception
{
    private static Logger log = LoggerFactory.getLogger(DrupalServicesResponseException.class);
    private int statusCode;
    private String reasonPhrase;

    public DrupalServicesResponseException(int statusCode, String reasonPhrase)
    {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        log.error(getMessage());
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

    @Override
    public String getMessage()
    {
        return statusCode + ": " + reasonPhrase;
    }
}
