package com.example.konyvtar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiService {
    @GET("/05kYKz/books")
    Call<List<Book>> getBooks();

    @POST("/05kYKz/books")
    Call<Book> createBook(@Body Book books);

    @DELETE("/05kYKz/books/{id}")
    Call<Void> deleteBook(@Path("id") int id);

    @PATCH("/05kYKz/books/{id}")
    Call<Book> editBook(@Path("id") int id, @Body Book books);
}
