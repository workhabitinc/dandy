package org.workhabit.drupal.api.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 11:00:08 AM
 */
@SuppressWarnings({"WeakerAccess"})
public class UnixTimeDateAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Date(jsonElement.getAsJsonPrimitive().getAsLong() * 1000);
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive((int)(src.getTime() / 1000));
    }
}
