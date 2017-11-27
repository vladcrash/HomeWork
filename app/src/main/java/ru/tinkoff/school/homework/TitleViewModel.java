package ru.tinkoff.school.homework;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Влад on 28.11.2017.
 */

public class TitleViewModel extends BaseObservable{
    private Payload mPayload;

    public TitleViewModel(Payload payload) {
        mPayload = payload;
    }

    @Bindable
    public String getTitle() {
        return mPayload.getText();
    }

    public void setTitle(String title) {
        mPayload.setText(title);
        notifyPropertyChanged(BR.title);
    }
}
