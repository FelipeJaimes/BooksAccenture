package com.example.android.booksaccenture.rest.service;

import com.example.android.booksaccenture.rest.api.BookAPI;
import com.example.android.booksaccenture.rest.response.BookResponse;

import retrofit2.Callback;

public class BookService extends BaseService<BookAPI> {

    public BookService() {
        super(BookAPI.BASE_URL, BookAPI.class);
    }

    public void getBooks(String searchTerm, Callback<BookResponse> bookResponseCall) {
        getAPI().getBooks(searchTerm).enqueue(bookResponseCall);
    }
}
