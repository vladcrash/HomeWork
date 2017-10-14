package ru.tinkoff.school.homework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Влад on 14.10.2017.
 */

public class InputFragment extends Fragment {
    private static final String REQUEST_CODE = "request_code";

    private TextInputEditText mNumber;
    private Button mOk;
    private GoNextListener mListener;

    public static InputFragment newInstance(int requestCode) {
        Bundle args = new Bundle();
        args.putInt(REQUEST_CODE, requestCode);

        InputFragment fragment = new InputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (GoNextListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " should implement GoNextListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        mNumber = (TextInputEditText) view.findViewById(R.id.number_et);
        mOk = (Button) view.findViewById(R.id.ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goNext(mNumber.getText().toString(), getArguments().getInt(REQUEST_CODE));
            }
        });
        return view;

    }
}
