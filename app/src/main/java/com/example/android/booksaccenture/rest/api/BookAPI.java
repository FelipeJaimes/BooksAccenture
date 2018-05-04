package com.example.android.booksaccenture.rest.api;

import com.example.android.booksaccenture.model.Book;
import com.example.android.booksaccenture.rest.response.BookResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookAPI {

    public static final String BASE_URL = "http://it-ebooks-api.info";

    @GET("/v1/search/{search}")
    Call<BookResponse> getBooks(@Path("search") String search);

}
