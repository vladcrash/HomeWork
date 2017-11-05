package ru.tinkoff.school.homework;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Created by Влад on 05.11.2017.
 */

public class MyCustomDeserializer2 implements JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String temp = json.getAsString().replace(',', '.');
        return new BigDecimal(temp);
    }
}
