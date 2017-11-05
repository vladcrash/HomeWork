package ru.tinkoff.school.homework;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by Влад on 05.11.2017.
 */

public class MyExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == Human.class && f.getName().equals("age"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz == boolean.class;
    }
}
