package com.example.loginjava.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// A basic setup for Retrofit.
public class RetrofitClient {
    private static final String BASE_URL = "https://your-api-base-url.com/"; // 🔴 CHANGE THIS TO YOUR API URL

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ApiService apiService = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }
}