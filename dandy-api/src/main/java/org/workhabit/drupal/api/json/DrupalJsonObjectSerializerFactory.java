package org.workhabit.drupal.api.json;

import org.workhabit.drupal.api.entity.DrupalEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/30/10, 12:26 PM
 */
public class DrupalJsonObjectSerializerFactory
{
    private static final Map<Class<? extends DrupalEntity>, DrupalJsonObjectSerializer> instances = new HashMap<Class<? extends DrupalEntity>, DrupalJsonObjectSerializer>();

    public static <T extends DrupalEntity> DrupalJsonObjectSerializer<T> getInstance(Class<T> clazz)
    {
        if (!instances.containsKey(clazz)) {
            DrupalJsonObjectSerializer<T> serializer = new DrupalJsonObjectSerializer<T>(clazz);
            instances.put(clazz, serializer);
        }
        //noinspection unchecked
        return instances.get(clazz);
    }

    private DrupalJsonObjectSerializerFactory()
    {
        // not instantiable
    }
}
