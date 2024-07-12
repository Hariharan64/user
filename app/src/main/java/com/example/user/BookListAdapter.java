package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class BookListAdapter extends AppCompatActivity implements ListAdapter {

    private Activity context;
    private List<MainActivity.Book> bookList;

    public BookListAdapter(Activity context, List<MainActivity.Book> bookList) {
        super();
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.book_list_item, null, true);

        TextView textViewTitle = listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewAuthor = listViewItem.findViewById(R.id.textViewAuthor);
        TextView textViewDescription = listViewItem.findViewById(R.id.textViewDescription);

        MainActivity.Book book = bookList.get(position);
        textViewTitle.setText(book.title);
        textViewAuthor.setText(book.author);
        textViewDescription.setText(book.description);

        return listViewItem;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}