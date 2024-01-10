package com.example.iaq;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/master/info")
    Call<Status> getInfo();
}
