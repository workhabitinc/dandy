package org.workhabit.drupal.api.site.v3.local;

import org.doomdark.uuid.UUIDGenerator;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 2/16/11, 11:51 AM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class TestData
{
    public static String getTestTitle() {
        return "JUnit" + UUIDGenerator.getInstance().generateTimeBasedUUID();
    }
}
