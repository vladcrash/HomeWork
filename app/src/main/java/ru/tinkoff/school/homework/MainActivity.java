package ru.tinkoff.school.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstPart();
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
}
