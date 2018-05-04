package com.example.android.booksaccenture.ui.listener;

import android.view.View;

import com.example.android.booksaccenture.model.Book;

public interface ItemClickListener {

    void onItemClick(View view, int adapterPos, Book item);
}
