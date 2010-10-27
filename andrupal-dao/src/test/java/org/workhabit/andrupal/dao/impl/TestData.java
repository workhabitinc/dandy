package org.workhabit.andrupal.dao.impl;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 2:52:58 PM
 */
public class TestData {
    public static int getTestId() {
        return (int) Math.floor(Math.random() * 100000);
    }

    public static String getTestText() {
        return Integer.toString(getTestId(), 16);
    }
}
