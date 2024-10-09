package com.example.hydrotool_app.api;

import com.example.hydrotool_app.domain.UserAuth;
import com.example.hydrotool_app.requests.UserAuthLoginRequestBody;
import com.example.hydrotool_app.requests.UserAuthPostRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAuthApi {
    @POST("login") // Altere para o endpoint correto de login
    Call<UserAuth> loginUser(@Body UserAuthLoginRequestBody userAuthLoginRequestBody);

    @POST("register") // Endpoint para registro
    Call<UserAuth> registerUser(@Body UserAuthPostRequestBody userAuthPostRequestBody);
}
