package org.workhabit.drupal.api.json;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 24, 2010, 4:56:31 PM
 */
public class BooleanAdapter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src ? 1 : 0);
    }

    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsInt() == 1;
    }
}
