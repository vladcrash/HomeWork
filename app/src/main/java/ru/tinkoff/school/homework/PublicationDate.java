package ru.tinkoff.school.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Влад on 27.11.2017.
 */

public class PublicationDate {

    @SerializedName("milliseconds")
    @Expose
    private long milliseconds;

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
