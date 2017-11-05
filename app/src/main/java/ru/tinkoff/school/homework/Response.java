package ru.tinkoff.school.homework;

import com.google.gson.JsonElement;

import java.util.Map;

/**
 * Created by Влад on 05.11.2017.
 */

public class Response {
    private String name;
    private Map<String, JsonElement> any_map;

    @Override
    public String toString() {
        return name + " " + any_map.toString();
    }
}
