package ru.tinkoff.school.homework;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by Влад on 12.11.2017.
 */

public class App extends Application {
    private static final String DB_NAME = "NodeDatabase";

    private static NodeDatabase sNodeDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sNodeDatabase = Room.databaseBuilder(getApplicationContext(), NodeDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static NodeDatabase getNodeDatabase() {
        return sNodeDatabase;
    }
}
