package com.example.android.booksaccenture.rest.api;

import com.example.android.booksaccenture.rest.response.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookAPI {
    String BASE_URL = "http://it-ebooks-api.info";

    @GET("/v1/search/{search}")
    Call<BookResponse> getBooks(@Path("search") String search);
}
