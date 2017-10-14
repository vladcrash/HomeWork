package ru.tinkoff.school.homework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements GoNextListener{
    private static final int REQUEST_FIRST_NUMBER = 0;
    private static final int REQUEST_SECOND_NUMBER = 1;
    private static final int REQUEST_TYPE_OPERATION = 2;

    private double mFirstNumber = 0.0;
    private double mSecondNumber = 0.0;
    private char mTypeOperation = '+';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment(InputFragment.newInstance(REQUEST_FIRST_NUMBER));
    }

    private void createFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_first_fragment:
                createFragment(InputFragment.newInstance(REQUEST_FIRST_NUMBER));
                break;
            case R.id.show_second_fragment:
                createFragment(InputFragment.newInstance(REQUEST_SECOND_NUMBER));
                break;
            case R.id.show_third_fragment:
                createFragment(OperationFragment.newInstance(REQUEST_TYPE_OPERATION));
                break;
            case R.id.show_fourth_fragment:
                createFragment(ResultFragment.newInstance(mFirstNumber, mSecondNumber, mTypeOperation));
                break;
        }
    }

    @Override
    public void goNext(String next, int requestCode) {
        switch (requestCode) {
            case REQUEST_FIRST_NUMBER:
                if (next.length() != 0) {
                    mFirstNumber = Double.valueOf(next);
                }
                createFragment(InputFragment.newInstance(REQUEST_SECOND_NUMBER));
                break;
            case REQUEST_SECOND_NUMBER:
                if (next.length() != 0) {
                    mSecondNumber = Double.valueOf(next);
                }
                createFragment(OperationFragment.newInstance(REQUEST_TYPE_OPERATION));
                break;
            case REQUEST_TYPE_OPERATION:
                mTypeOperation = next.charAt(0);
                createFragment(ResultFragment.newInstance(mFirstNumber, mSecondNumber, mTypeOperation));
                break;
        }
    }

}
