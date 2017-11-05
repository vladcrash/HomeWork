package ru.tinkoff.school.homework;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by Влад on 05.11.2017.
 */

public class Response2 {

    @SerializedName("money_amount")
    private BigDecimal moneyAmount;

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }
}
