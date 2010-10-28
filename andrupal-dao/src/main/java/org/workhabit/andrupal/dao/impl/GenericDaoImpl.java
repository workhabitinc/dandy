package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.workhabit.andrupal.dao.GenericDao;
import org.workhabit.drupal.api.annotations.IdFieldName;
import org.workhabit.drupal.api.entity.DrupalEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:33:47 PM
 */
public class GenericDaoImpl<C extends DrupalEntity> extends BaseDaoImpl<C, String> implements GenericDao<C> {

    protected GenericDaoImpl(ConnectionSource connectionSource, Class<C> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        try {
            TableUtils.createTable(connectionSource, dataClass);
        } catch (SQLException e) {
            // OK, since the table might exist already.
        }
    }

    public int save(C data) throws SQLException {
        if (super.queryForId(data.getId()) != null) {
            return super.update(data);
        } else {
            return super.create(data);
        }
    }

    public List<C> getAll() throws SQLException {
        IdFieldName annotation = getDataClass().getAnnotation(IdFieldName.class);
        if (annotation == null || annotation.value() == null) {
            throw new RuntimeException("Class " + getDataClass().getName() + " is missing @IdFieldName annotation.  Cannot autowire query for ID field.");
        }
        String orderBy = annotation.value();
        return getAll(orderBy, true);
    }

    public List<Integer> saveAll(List<C> dataList) throws SQLException {
        List<Integer> results = new ArrayList<Integer>();
        for (C data : dataList) {
            if (super.queryForId(data.getId()) != null) {
                results.add(super.update(data));
            } else {
                results.add(super.create(data));
            }
        }
        return results;
    }

    public List<C> getAll(String orderBy, boolean ascending) throws SQLException {
        QueryBuilder<C, String> builder = this.queryBuilder();
        builder.orderBy(orderBy, ascending);
        return super.query(builder.prepare());
    }
}
