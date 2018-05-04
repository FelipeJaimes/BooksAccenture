package com.example.android.booksaccenture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;
import com.example.android.booksaccenture.rest.api.BookAPI;
import com.example.android.booksaccenture.rest.response.BookResponse;
import com.example.android.booksaccenture.ui.adapter.BookAdapterLW;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mEmptyStateTextView;
    private List<Book> books;

    //    LISTVIEW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private BookAdapterLW mBookAdapterLW;
//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        LISTVIEW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        bookListView.setEmptyView(mEmptyStateTextView);

        mBookAdapterLW = new BookAdapterLW(this, new ArrayList<Book>());
        bookListView.setAdapter(mBookAdapterLW);
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//        RECYCLERVIEW------------------------------------------------------------------------------
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//
//        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view); //TODO
//        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        ------------------------------------------------------------------------------------------


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

                mBookAdapterLW.clear();

                books = response.body().getBooks();

                if (books == null) {
                    Log.d("ERROR: ", "empty array");
                } else {
                    mBookAdapterLW.addAll(books);
                }

            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR: ", t.getMessage());
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//LISTVIEW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book currentBook = mBookAdapterLW.getItem(position);
                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra("currentBook", currentBook);
                startActivity(intent);

            }
        });
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//RECYCLERVIEW--------------------------------------------------------------------------------------
//        BookAdapter adapter = new BookAdapter(books);
//        adapter.setOnItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int adapterPos, Book item) {
//
//            }
//        });
//        recyclerView.setAdapter(adapter);
//--------------------------------------------------------------------------------------------------
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

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

