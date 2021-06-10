package com.example.myapplication.datalayer;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ParseManager {
    public final String TAG = getClass().getSimpleName();

    private Gson gson = new GsonBuilder()
            .setLenient()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    private Gson screenGson = new GsonBuilder()
            .setLenient()
            .create();

    private static ParseManager instance = new ParseManager();

    public static ParseManager getInstance() {
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public Gson getScreenGson() {
        return screenGson;
    }

    //Метод конвертации списка JsonArray - > ArrayList
    public static <E> ArrayList<E> jsonArrayToArrayList(JsonArray jsonArray, Class<E[]> cls) {
        return new ArrayList<>(
                Arrays.asList(instance.screenGson.fromJson(jsonArray, cls))
        );
    }

    //Парсинг ГСОНА в Java obj
    public static <E> E jsonObjectToObject(JsonObject jsonObject, Class<E> cls) {
        return instance.screenGson.fromJson(jsonObject, cls);
    }
}