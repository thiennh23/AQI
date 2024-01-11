package com.example.iaq;

import com.example.iaq.Models.Status;
import com.example.iaq.Models.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @GET("api/master/info")
    Call<Status> getInfo();

    @POST("auth/realms/master/protocol/openid-connect/token")
    @FormUrlEncoded
    Call<Token> login(
            @Field("client_id") String clientId,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );
}
