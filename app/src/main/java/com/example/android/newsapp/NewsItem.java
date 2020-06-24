package com.example.android.newsapp;

public class NewsItem {
    String title;
    String section;
    String date;
    String url;
    String author;
    int authorsCount;

    public NewsItem(String title, String section, String date, String url, String author,int authorsCount) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.url = url;
        this.author=author;
        this.authorsCount=authorsCount;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() { return url; }

    public String getAuthor() { return author; }

    public int getAuthorsCount() { return authorsCount; }
}
