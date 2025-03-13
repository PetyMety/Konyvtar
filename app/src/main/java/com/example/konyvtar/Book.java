package com.example.konyvtar;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String Title;
    private String Author;
    private int Page_count;
    private int Release_year;

    public Book( String title, String author, int page_count, int release_year) {
        this.Title = title;
        this.Author = author;
        this.Page_count = page_count;
        this.Release_year = release_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public int getPage_count() {
        return Page_count;
    }

    public void setPage_count(int page_count) {
        this.Page_count = page_count;
    }

    public int getRelease_year() {
        return Release_year;
    }

    public void setRelease_year(int release_year) {
        this.Release_year = release_year;
    }
}
