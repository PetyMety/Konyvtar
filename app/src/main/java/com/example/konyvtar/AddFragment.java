package com.example.konyvtar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Callback;


public class AddFragment extends Fragment {
    private View view;
    private EditText titleEditText;
    private EditText authorEditText;
    private EditText pageCountEditText;
    private EditText releaseYearEditText;

    private Button addButton;

    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        view = inflater.inflate(R.layout.fragment_add, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        authorEditText = view.findViewById(R.id.authorEditText);
        titleEditText = view.findViewById(R.id.titleEditText);
        pageCountEditText = view.findViewById(R.id.pageCountEditText);
        releaseYearEditText = view.findViewById(R.id.releaseYearEditText);
        addButton = view.findViewById(R.id.addButton);
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addBook(apiService);
            }
        });
    }
    private void addBook(RetrofitApiService apiService) {
        String title = titleEditText.getText().toString();
        String author = authorEditText.getText().toString();
        int pageCount = Integer.parseInt(pageCountEditText.getText().toString());
        int releaseYear = Integer.parseInt(releaseYearEditText.getText().toString());
        Book book = new Book(title, author, pageCount, releaseYear);
        apiService.createBook(book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(retrofit2.Call<Book> call, retrofit2.Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Könyv sikeresen hozzáadva", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Könyv hozzáadás sikertelen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Book> call, Throwable t) {
                Toast.makeText(getContext(), "Könyv hozzáadás sikertelen", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
