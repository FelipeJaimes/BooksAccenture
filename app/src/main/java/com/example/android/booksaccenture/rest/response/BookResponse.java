package com.example.android.booksaccenture.rest.response;

import com.example.android.booksaccenture.model.Book;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {

    @SerializedName("Error")
    @Expose
    private String error;
    @SerializedName("Time")
    @Expose
    private Double time;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Page")
    @Expose
    private Integer page;
    @SerializedName("Books")
    @Expose
    private List<Book> books = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
