package ru.tinkoff.school.homework;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Plot plot = (Plot) findViewById(R.id.plot);
        List<PointF> dots = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            dots.add(new PointF(i, i * i));
        }
        plot.setDots(dots);
    }
}
