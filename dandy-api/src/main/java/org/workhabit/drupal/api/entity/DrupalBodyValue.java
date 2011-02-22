package org.workhabit.drupal.api.entity;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/8/11, 12:46 PM
 */
public class DrupalBodyValue
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

    @SuppressWarnings({"UnusedDeclaration"})
    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getSafeValue()
    {
        return safeValue;
    }

    public void setSafeValue(String safeValue)
    {
        this.safeValue = safeValue;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getSafeSummary()
    {
        return safeSummary;
    }

    public void setSafeSummary(String safeSummary)
    {
        this.safeSummary = safeSummary;
    }
}
