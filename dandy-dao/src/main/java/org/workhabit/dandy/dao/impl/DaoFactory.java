package org.workhabit.dandy.dao.impl;

import com.j256.ormlite.support.ConnectionSource;
import org.workhabit.dandy.dao.GenericDao;
import org.workhabit.drupal.api.entity.DrupalEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:39:12 PM
 */
public class DaoFactory {
    private DaoFactory() {
        // no constructor, static object factory.
    }
    private static final Map<Class, GenericDao> daoMap = new HashMap<Class, GenericDao>();

    public static <T extends DrupalEntity> GenericDao<T> getInstanceForClass(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        if (!daoMap.containsKey(clazz)) {
            daoMap.put(clazz, new GenericDaoImpl<T>(connectionSource, clazz));
        }
        //noinspection unchecked
        return daoMap.get(clazz);
    }
}
