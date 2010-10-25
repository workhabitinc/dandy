package org.workhabit.drupal.api.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.workhabit.drupal.api.site.DrupalFetchException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:51:49 AM
 */
public class DrupalJsonObjectSerializer<T> {
    private Class<T> clazz;
    private Gson gson;

    public DrupalJsonObjectSerializer(Class<T> clazz) {
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
        return gson.fromJson(dataObject.toString(), clazz);
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
