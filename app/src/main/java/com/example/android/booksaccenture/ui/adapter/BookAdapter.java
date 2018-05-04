package com.example.android.booksaccenture.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.booksaccenture.R;
import com.example.android.booksaccenture.model.Book;
import com.example.android.booksaccenture.ui.listener.ItemClickListener;

import java.util.List;

import android.support.v7.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> mBooks;
    private ItemClickListener mItemClickListener;

    public BookAdapter(List<Book> items) {
        mBooks = items;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.titleTextView.setText(mBooks.get(position).getTitle());
        holder.idTextView.setText(mBooks.get(position).getID().toString());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView idTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            initUI(itemView);
            setViewHolderListener(this, itemView);
        }

        private void initUI(View view) {
            titleTextView = view.findViewById(R.id.book_list_item_title);
            idTextView = view.findViewById(R.id.book_list_item_id);
        }
    }

    private void setViewHolderListener(final RecyclerView.ViewHolder viewHolder, View itemView) {
        if (mItemClickListener != null)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mItemClickListener.onItemClick(view, viewHolder.getAdapterPosition(), mBooks.get(viewHolder.getAdapterPosition()));
                }
            });
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}


