package com.example.konyvtar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookEditAdapter extends BaseAdapter {
    private List<Book> bookList;
    private Context context;
    private Fragment fragment;
    private Class fragmentClass;

    public BookEditAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.bookedit, viewGroup, false);
        }
        TextView titleTextView = view.findViewById(R.id.title);
        TextView authorTextView = view.findViewById(R.id.author);
        TextView pageCountTextView = view.findViewById(R.id.page_count);
        TextView relesaseYearTextView = view.findViewById(R.id.relesase_year);
        Button editbutton = view.findViewById(R.id.edit_button);


        Book book = bookList.get(i);
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        pageCountTextView.setText(String.valueOf(book.getPage_count()));
        relesaseYearTextView.setText(String.valueOf(book.getRelease_year()));
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBook(book);
            }
        });


        return view;
    }
    public void editBook(Book book) {
        fragmentClass = EditOneFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (IllegalAccessException | InstantiationException e){
            throw new RuntimeException(e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", (Serializable) book);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fregment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

}
