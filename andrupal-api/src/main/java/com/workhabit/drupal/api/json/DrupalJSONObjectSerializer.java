package com.workhabit.drupal.api.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.workhabit.drupal.api.site.DrupalFetchException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 22, 2010, 10:51:49 AM
 */
public class DrupalJSONObjectSerializer<T> {
    private Class<T> clazz;
    private Gson gson;

    public DrupalJSONObjectSerializer(Class<T> clazz) {
        this.clazz = clazz;
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
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
        JSONObject jsonObject = extractDataObject(json);
        Type type = new TypeToken<ArrayList<T>>() {}.getType();
        return gson.fromJson(jsonObject.toString(), type);
    }

    private void assertNoErrors(JSONObject objectResult) throws JSONException, DrupalFetchException {
        if (objectResult.has("#error") && objectResult.getBoolean("#error")) {
            throw new DrupalFetchException(objectResult);
        }
    }
}
