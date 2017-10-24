package ru.tinkoff.school.homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Влад on 24.10.2017.
 */

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {
    private static final String TAG = "ListFragment";
    public static final int LOADER_ID = 1;

    private Loader<List<String>> mLoader;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoader = this.getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        Log.i(TAG, "onCreate()");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mListView = v.findViewById(R.id.list_view);
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        mLoader = new FakeLoader(getActivity(), args);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        if (loader.getId() == LOADER_ID) {
            mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
            mListView.setAdapter(mAdapter);
        }
        Log.i(TAG, "I get it " + data);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

}
