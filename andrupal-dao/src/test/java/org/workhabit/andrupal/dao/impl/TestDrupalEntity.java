package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.junit.Ignore;
import org.workhabit.drupal.api.entity.DrupalEntity;


/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 1:42:43 PM
 */
@Ignore
@DatabaseTable(tableName = "TestDrupalEntity")
public class TestDrupalEntity implements DrupalEntity {
    public TestDrupalEntity() {
        super();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    @DatabaseField(id = true)
    private int id;

    public String getId() {
        return null;
    }
}

