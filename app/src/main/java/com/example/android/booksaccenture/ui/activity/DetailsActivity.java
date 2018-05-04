package com.example.android.booksaccenture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private ImageView bookImageView;
    private TextView idTextView;
    private TextView subTitleTextView;
    private TextView descriptionTextView;
    private TextView isbnTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        Book mBook = intent.getExtras().getParcelable("currentBook");

        titleTextView = findViewById(R.id.book_details_title);
        titleTextView.setText(mBook.getTitle());

        bookImageView = findViewById(R.id.book_details_image);
        Picasso.get().load(mBook.getImage()).into(bookImageView);

        idTextView = findViewById(R.id.book_details_id);
        idTextView.setText(mBook.getID().toString());

        subTitleTextView = findViewById(R.id.book_details_subtitle);
        subTitleTextView.setText(mBook.getSubTitle());

        descriptionTextView = findViewById(R.id.book_details_description);
        descriptionTextView.setText(mBook.getDescription());

        isbnTextView = findViewById(R.id.book_details_isbn);
        isbnTextView.setText(mBook.getIsbn());

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
