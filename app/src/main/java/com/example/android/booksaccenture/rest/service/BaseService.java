package com.example.android.booksaccenture.rest.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

abstract class BaseService <API> {
    private Retrofit mRetrofit;
    private API api;

    BaseService(String baseUrl, final Class<API> apiClass) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = mRetrofit.create(apiClass);
    }

    API getAPI() {
        return api;
    }
}
