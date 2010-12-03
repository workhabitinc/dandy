package org.workhabit.drupal.api.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.workhabit.drupal.api.entity.DrupalField;
import org.workhabit.drupal.api.entity.DrupalNode;
import org.workhabit.drupal.api.site.exceptions.DrupalFetchException;

import java.util.*;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:51:49 AM
 */
public class DrupalJsonObjectSerializer<T> {
    private Class<T> clazz;
    private Gson gson;

    DrupalJsonObjectSerializer(Class<T> clazz) {
        this.clazz = clazz;
        GsonBuilder builder = new GsonBuilder();
        UnixTimeDateAdapter dateAdapter = new UnixTimeDateAdapter();
        builder.registerTypeAdapter(Date.class, dateAdapter);
        BooleanAdapter booleanAdapter = new BooleanAdapter();
        builder.registerTypeAdapter(Boolean.class, booleanAdapter);
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.create();
    }

    public String serialize(T object) {
        return gson.toJson(object);
    }

    public T unserialize(String json) throws DrupalFetchException, JSONException {
        JSONObject dataObject = extractDataObject(json);
        T t = gson.fromJson(dataObject.toString(), clazz);
        if (clazz == DrupalNode.class) {
            // special handling for cck fields. TODO: refactor this into an interceptor pattern.
            Iterator keys = dataObject.keys();
            ArrayList<DrupalField> fields = new ArrayList<DrupalField>();
            while (keys.hasNext()) {
                String name = (String) keys.next();
                if (name.startsWith("field_")) {
                    // process cck field
                    //
                    DrupalField field = new DrupalField();
                    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
                    JSONArray cckFieldArray = dataObject.getJSONArray(name);
                    for (int i = 0; i < cckFieldArray.length(); i++) {
                        JSONObject o = cckFieldArray.getJSONObject(i);
                        field.setName(name);
                        Iterator objectKeys = o.keys();
                        HashMap<String, String> valueMap = new HashMap<String, String>();
                        while (objectKeys.hasNext()) {
                            String next = (String) objectKeys.next();
                            valueMap.put(next, o.getString(next));
                        }
                        values.add(valueMap);
                    }
                    field.setValues(values);
                    fields.add(field);
                }

            }
            DrupalNode n = (DrupalNode) t;
            n.setFields(fields);
        }
        return t;
    }

    private JSONObject extractDataObject(String json) throws JSONException, DrupalFetchException {
        JSONObject objectResult = new JSONObject(json);
        assertNoErrors(objectResult);
        return objectResult.getJSONObject("#data");
    }

    public List<T> unserializeList(String json) throws DrupalFetchException, JSONException {
        JSONArray jsonArray = extractDataArray(json);
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), clazz));
        }
        return list;
    }

    private JSONArray extractDataArray(String json) throws JSONException, DrupalFetchException {
        JSONObject objectResult = new JSONObject(json);
        assertNoErrors(objectResult);
        return objectResult.getJSONArray("#data");
    }

    private void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult);
        }
    }
}
