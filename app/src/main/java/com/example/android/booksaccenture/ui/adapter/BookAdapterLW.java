package com.example.android.booksaccenture.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;

import java.util.ArrayList;

public class BookAdapterLW extends ArrayAdapter<Book> {


    public BookAdapterLW(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView idTextView = (TextView) listItemView.findViewById(R.id.book_list_item_id);
        idTextView.setText(currentBook.getID().toString());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.book_list_item_title);
        titleTextView.setText(currentBook.getTitle());

        return listItemView;

    }
}

