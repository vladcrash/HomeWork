package ru.tinkoff.school.homework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Влад on 14.10.2017.
 */

public class OperationFragment extends Fragment {
    private static final String REQUEST_CODE = "request_code";

    private Button mAdd;
    private Button mSubtract;
    private Button mMultiply;
    private Button mDivide;
    private GoNextListener mListener;

    public static OperationFragment newInstance(int requestCode) {
        Bundle args = new Bundle();
        args.putInt(REQUEST_CODE, requestCode);

        OperationFragment fragment = new OperationFragment();
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
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        mAdd = (Button) view.findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goNext(mAdd.getText().toString(), getArguments().getInt(REQUEST_CODE));
            }
        });

        mSubtract = (Button) view.findViewById(R.id.subtract);
        mSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goNext(mSubtract.getText().toString(), getArguments().getInt(REQUEST_CODE));
            }
        });

        mMultiply = (Button) view.findViewById(R.id.multiply);
        mMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goNext(mMultiply.getText().toString(), getArguments().getInt(REQUEST_CODE));
            }
        });

        mDivide = (Button) view.findViewById(R.id.divide);
        mDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goNext(mDivide.getText().toString(), getArguments().getInt(REQUEST_CODE));
            }
        });
        return view;
    }
}
