package org.workhabit.dandy.dao;

import com.j256.ormlite.dao.Dao;
import org.workhabit.drupal.api.entity.DrupalEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 28, 2010, 2:10:48 PM
 */
public interface GenericDao<C extends DrupalEntity> extends Dao<C, String> {
    int save(C data) throws SQLException;

    List<C> getAll() throws SQLException;

    List<Integer> saveAll(List<C> dataList) throws SQLException;

    List<C> getAll(String orderBy, boolean ascending) throws SQLException;
}
