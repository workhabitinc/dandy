package org.workhabit.andrupal.dao.impl;

import org.junit.Ignore;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 2:52:58 PM
 */
@Ignore
public class TestData {
    public static int getTestInt() {
        return (int) Math.floor(Math.random() * 100000);
    }

    public static String getTestText() {
        return Integer.toString(getTestInt(), 16);
    }
}
