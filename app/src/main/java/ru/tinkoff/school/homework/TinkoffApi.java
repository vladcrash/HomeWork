package ru.tinkoff.school.homework;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Влад on 28.11.2017.
 */

public interface TinkoffApi {
    @GET("v1/news")
    Call<ApiResponse> getNews();
}
