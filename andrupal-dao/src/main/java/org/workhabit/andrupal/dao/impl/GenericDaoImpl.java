package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.workhabit.drupal.api.entity.DrupalEntity;

import java.sql.SQLException;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:33:47 PM
 */
public class GenericDaoImpl<C extends DrupalEntity> extends BaseDaoImpl<C, String> {
    private ConnectionSource connectionSource;

    protected GenericDaoImpl(ConnectionSource connectionSource, Class<C> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        this.connectionSource = connectionSource;
        try {
            TableUtils.createTable(this.connectionSource, dataClass);
        } catch (SQLException e) {
            // OK, since the table might exist already.
        }
    }

    @Override
    public int create(C data) throws SQLException {
        return super.create(data);
    }
}
