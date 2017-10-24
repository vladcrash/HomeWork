package ru.tinkoff.school.homework;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Влад on 24.10.2017.
 */

public class FakeLoader extends AsyncTaskLoader<List<String>> {
    private static final String TAG = "FakeLoader";

    public FakeLoader(Context context, Bundle args) {
        super(context);
    }

    @Override
    public List<String> loadInBackground() {
        Log.i(TAG, "is started");

        List<String> listItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listItems.add("item " + i);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "is done");
        return listItems;
    }

    @Override
    public void forceLoad() {
        Log.i(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(List<String> data) {
        Log.i(TAG, "deliverResult");
        super.deliverResult(data);
    }

    @Override
    protected boolean onCancelLoad() {
        Log.i(TAG, "onCancelLoad");
        return super.onCancelLoad();
    }
}
