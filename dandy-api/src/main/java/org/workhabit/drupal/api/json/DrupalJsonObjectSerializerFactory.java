package org.workhabit.drupal.api.json;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/30/10, 12:26 PM
 */
public class DrupalJsonObjectSerializerFactory {
    private static Map<Class, DrupalJsonObjectSerializer> instances = new HashMap<Class, DrupalJsonObjectSerializer>();

    public static <T> DrupalJsonObjectSerializer<T> getInstance(Class<T> clazz) {
        if (!instances.containsKey(clazz)) {
            DrupalJsonObjectSerializer<T> serializer = new DrupalJsonObjectSerializer<T>(clazz);
            instances.put(clazz, serializer);
        }
        return instances.get(clazz);
    }
}
