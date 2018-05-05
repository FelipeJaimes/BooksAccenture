package com.example.android.booksaccenture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;
import com.example.android.booksaccenture.rest.response.BookResponse;
import com.example.android.booksaccenture.rest.service.BookService;
import com.example.android.booksaccenture.ui.adapter.BookAdapter;
import com.example.android.booksaccenture.ui.listener.ItemClickListener;
import com.example.android.booksaccenture.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.empty_view) TextView mEmptyStateTextView;

    private List<Book> books = new ArrayList<>();
    private BookAdapter mBookAdapter;

    RecyclerView recyclerView;
    BookService bookService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra("SEARCH_TERM");
        bookService = new BookService();

        if (!NetworkUtils.checkInternetConnection(this)) {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            return;
        }

        searchBooks(searchTerm, bookService);

        mBookAdapter = new BookAdapter(books);
        mBookAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPos, Book item) {
                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra("currentBook", item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mBookAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void searchBooks(String searchTerm, BookService bookService) {
        bookService.getBooks(searchTerm, new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.body().getBooks() == null) {
                    recyclerView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_books_found);
                    Log.d("ERROR: ", "empty array");
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    books.addAll(response.body().getBooks());
                    mBookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR: ", t.getMessage());
            }
        });
    }

}

