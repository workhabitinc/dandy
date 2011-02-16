package org.workhabit.drupal.http;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/15/11, 12:31 PM
 */
public class ServicesResponse
{
    private String responseBody;
    private int statusCode;
    private String reasonPhrase;

    public String getResponseBody()
    {
        return responseBody;
    }

    public void setResponseBody(String responseBody)
    {
        this.responseBody = responseBody;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public void setReasonPhrase(String reasonPhrase)
    {
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
}
