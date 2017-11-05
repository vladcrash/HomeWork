package ru.tinkoff.school.homework;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * Created by Влад on 05.11.2017.
 */

public class MyCustomSerializer implements JsonSerializer<DateExample> {
    @Override
    public JsonElement serialize(DateExample src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.addProperty("date", sdf.format(src.getDate()));
        return jsonObject;
    }
}
