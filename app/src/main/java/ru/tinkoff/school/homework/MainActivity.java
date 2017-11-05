package ru.tinkoff.school.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstPart();
        secondPart();
        thirdPart();
        fourthPart();
    }

    private void firstPart() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setExclusionStrategies(new MyExclusionStrategy())
                .create();

        Human human = new Human(21, "Vlad", false, true);
        String json = gson.toJson(human);
        Log.i(TAG, json);
    }

    private void secondPart() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Map.class, new MyCustomDeserializer())
                .create();

        String json = "{'name':'name','any_map':{'a':'55','b':'85','c':'56'}}";
        Response response = gson.fromJson(json, Response.class);

        Log.i(TAG, response.toString());
    }

    private void thirdPart() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BigDecimal.class, new MyCustomDeserializer2())
                .create();

        String json = "{'money_amount':'2448,88'}";
        Response2 response = gson.fromJson(json, Response2.class);

        Log.i(TAG, response.getMoneyAmount().toString());
    }

    private void fourthPart() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(DateExample.class, new MyCustomSerializer())
                .create();

        DateExample date = new DateExample(new Date());
        String json = gson.toJson(date);

        Log.i(TAG, json);
    }
}
