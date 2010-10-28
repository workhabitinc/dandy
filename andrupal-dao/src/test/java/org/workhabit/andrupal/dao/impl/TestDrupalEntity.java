package org.workhabit.andrupal.dao.impl;

import org.junit.Ignore;
import org.workhabit.drupal.api.entity.DrupalEntity;

import javax.persistence.Id;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 1:42:43 PM
 */
@Ignore
public class TestDrupalEntity implements DrupalEntity {
    public TestDrupalEntity() {
        super();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    @Id
    private int id;

    public String getId() {
        return null;
    }
}

