package org.workhabit.drupal.api.entity.drupal7;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:46 PM
 */
public class DrupalBody
{
    private String value;
    private String summary;
    private String format;
    private String safeValue;
    private String safeSummary;

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getSafeValue()
    {
        return safeValue;
    }

    public void setSafeValue(String safeValue)
    {
        this.safeValue = safeValue;
    }

    public String getSafeSummary()
    {
        return safeSummary;
    }

    public void setSafeSummary(String safeSummary)
    {
        this.safeSummary = safeSummary;
    }
}
