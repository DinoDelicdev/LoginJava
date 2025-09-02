package com.example.loginjava.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// This interface defines all the API endpoints for your app.
public interface ApiService {

    // This defines the login request.
    // @POST("login") means it will make a POST request to your BASE_URL + "login".
    // @Body means the LoginRequest object will be sent as the request's body.
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}