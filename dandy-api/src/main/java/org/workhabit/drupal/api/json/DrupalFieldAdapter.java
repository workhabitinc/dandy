package org.workhabit.drupal.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.workhabit.drupal.api.entity.DrupalField;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 12/22/10, 1:14 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DrupalFieldAdapter implements JsonSerializer<ArrayList<DrupalField>> {
    public JsonElement serialize(ArrayList<DrupalField> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retVal = new JsonObject();
        for (DrupalField drupalField : src) {
            String name = drupalField.getName();
            JsonObject fieldObject = new JsonObject();
            ArrayList<HashMap<String, String>> values = drupalField.getValues();
            for (int i = 0; i < values.size(); i++) {
                HashMap<String, String> map = values.get(i);
                JsonObject valueObject = new JsonObject();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    valueObject.addProperty(entry.getKey(), entry.getValue());
                }
                fieldObject.add(String.valueOf(i), valueObject);
            }
            retVal.add(name, fieldObject);
        }
        return retVal;
    }
}
