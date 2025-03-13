package com.example.konyvtar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditOneFragment extends Fragment {
    private Book book;
    private View view;
    private EditText titleEditText;
    private EditText authorEditText;
    private EditText pageCountEditText;
    private EditText releaseYearEditText;
    private Button editButton;

    public EditOneFragment() {
        // Required empty public constructor
    }

    public static EditOneFragment newInstance(String param1, String param2) {
        EditOneFragment fragment = new EditOneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = (Book) getArguments().getSerializable("book");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_one, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        titleEditText = view.findViewById(R.id.titleEditText);
        authorEditText = view.findViewById(R.id.authorEditText);
        pageCountEditText = view.findViewById(R.id.pageCountEditText);
        releaseYearEditText = view.findViewById(R.id.releaseYearEditText);
        editButton = view.findViewById(R.id.editButton);
        titleEditText.setText(book.getTitle());
        authorEditText.setText(book.getAuthor());
        pageCountEditText.setText(String.valueOf(book.getPage_count()));
        releaseYearEditText.setText(String.valueOf(book.getRelease_year()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBook(book, apiService);
            }
        });
    }
    private void editBook(Book book, RetrofitApiService apiService) {
        book.setTitle(titleEditText.getText().toString());
        book.setAuthor(authorEditText.getText().toString());
        book.setPage_count(Integer.parseInt(pageCountEditText.getText().toString()));
        book.setRelease_year(Integer.parseInt(releaseYearEditText.getText().toString()));
        apiService.editBook(book.getId(), book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(retrofit2.Call<Book> call, retrofit2.Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Könyv sikeresen szerkesztve", Toast.LENGTH_SHORT).show();
                    Class fragmentClass = EditFragment.class;
                    Fragment fragment = null;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    }catch (IllegalAccessException | java.lang.InstantiationException e){
                        throw new RuntimeException(e);
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fregment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                } else {
                    Toast.makeText(getContext(), "Könyv szerkesztés sikertelen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Book> call, Throwable t) {
                Toast.makeText(getContext(), "Könyv szerkesztés sikertelen", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
