package org.workhabit.drupal.api.site.exceptions;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/15/11, 2:42 PM
 */
public class DrupalServicesResponseException extends Exception
{
    private int statusCode;
    private String reasonPhrase;

    public DrupalServicesResponseException(int statusCode, String reasonPhrase)
    {

        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
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
