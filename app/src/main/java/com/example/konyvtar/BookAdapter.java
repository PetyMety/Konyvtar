package com.example.konyvtar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends BaseAdapter {
    private List<Book> bookList;
    private Context context;
    public BookAdapter(List<Book> bookList, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.booklist, viewGroup, false);
        }
        TextView titleTextView = view.findViewById(R.id.title);
        TextView authorTextView = view.findViewById(R.id.author);
        TextView pageCountTextView = view.findViewById(R.id.page_count);
        TextView relesaseYearTextView = view.findViewById(R.id.relesase_year);
        Button deleteButton = view.findViewById(R.id.delete_button);

        Book book = bookList.get(i);
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        pageCountTextView.setText(String.valueOf(book.getPage_count()));
        relesaseYearTextView.setText(String.valueOf(book.getRelease_year()));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(book.getId());
            }
        });

        return view;
    }
    public void deleteBook(int i){
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        apiService.deleteBook(i).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Könyv sikeresen törlve", Toast.LENGTH_SHORT).show();
                bookList.removeIf(book -> book.getId() == i);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Könyv törlés sikertelen", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
