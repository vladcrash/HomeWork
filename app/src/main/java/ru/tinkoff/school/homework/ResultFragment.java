package ru.tinkoff.school.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Влад on 14.10.2017.
 */

public class ResultFragment extends Fragment {
    private static final String FIRST_NUMBER = "first_number";
    private static final String SECOND_NUMBER = "second_number";
    private static final String TYPE_OPERATION = "type_operation";

    private TextView mResult;

    public static ResultFragment newInstance(double firstNumber, double secondNumber, char typeOperation) {
        Bundle args = new Bundle();
        args.putDouble(FIRST_NUMBER, firstNumber);
        args.putDouble(SECOND_NUMBER, secondNumber);
        args.putChar(TYPE_OPERATION, typeOperation);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mResult = (TextView) view.findViewById(R.id.result_tv);

        double firstNumber = getArguments().getDouble(FIRST_NUMBER);
        double secondNumber = getArguments().getDouble(SECOND_NUMBER);
        char typeOperation = getArguments().getChar(TYPE_OPERATION);
        double result = 0;

        switch (typeOperation) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
        }

        String resultInfo = firstNumber + " " + typeOperation + " " + secondNumber + " = " + result;
        mResult.setText(resultInfo);
        return view;
    }
}
