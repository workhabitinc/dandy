package org.workhabit.andrupal.dao.impl;

import com.j256.ormlite.support.ConnectionSource;
import org.workhabit.drupal.api.entity.DrupalEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 27, 2010, 1:39:12 PM
 */
public class DaoFactory {
    private static Map<Class, GenericDaoImpl> daoMap = new HashMap<Class, GenericDaoImpl>();

    public static <T extends DrupalEntity> GenericDaoImpl<T> getInstanceForClass(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        if (!daoMap.containsKey(clazz)) {
            daoMap.put(clazz, new GenericDaoImpl<T>(connectionSource, clazz));
        }
        //noinspection unchecked
        return daoMap.get(clazz);
    }
}
