package org.workhabit.drupal.api.site;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/17/10, 3:15 PM
 */
public class DefaultCalendarFactory implements CalendarFactory {
    public Date getInstance() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
    }
}
