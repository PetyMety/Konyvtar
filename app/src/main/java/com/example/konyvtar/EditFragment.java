package com.example.konyvtar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFragment extends Fragment {
    private ListView bookListView;
    private List<Book> bookList;
    private BookEditAdapter bookEditAdapter;
    private View view;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        bookList = new ArrayList<>();
        bookEditAdapter = new BookEditAdapter(bookList, getContext());
        bookListView = view.findViewById(R.id.bookListView);
        bookListView.setAdapter(bookEditAdapter);

        loadBook(apiService);
    }
    public void loadBook(RetrofitApiService apiService){
        apiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    bookList.addAll(response.body());
                    bookEditAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "Nem sikerült betölteni a könyvlistát, null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Nem sikerült betölteni a könyvlistát", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
