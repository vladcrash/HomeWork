package ru.tinkoff.school.homework;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Влад on 05.11.2017.
 */

public class MyCustomDeserializer implements JsonDeserializer<Map<String, JsonElement>> {

    @Override
    public Map<String, JsonElement> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, JsonElement> hashMap = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            hashMap.put(entry.getKey(), entry.getValue());
        }

        return hashMap;
    }
}
