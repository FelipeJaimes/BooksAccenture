package com.example.android.booksaccenture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;
import com.example.android.booksaccenture.rest.api.BookAPI;
import com.example.android.booksaccenture.rest.response.BookResponse;
import com.example.android.booksaccenture.ui.adapter.BookAdapter;
import com.example.android.booksaccenture.ui.listener.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_view)
    TextView mEmptyStateTextView;
    private List<Book> books = new ArrayList<>();
    private BookAdapter mBookAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //RETROFIT++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra("SEARCH_TERM");

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BookAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookAPI api = mRetrofit.create(BookAPI.class);
        Call<BookResponse> call = api.getBooks(searchTerm);

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {

                mProgressBar.setVisibility(View.GONE);

                if (checkInternetConnection()) {
                    mEmptyStateTextView.setText("No books found");
                } else {
                    mEmptyStateTextView.setText("No internet connection");
                }

                if (response.body().getBooks() == null) {
                    recyclerView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
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
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        mBookAdapter = new BookAdapter(books);
        mBookAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPos, Book item) {
                //TODO
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

    //TODO: add to utils
    public boolean checkInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return connected = true;
        } else
            return connected = false;
    }
}

