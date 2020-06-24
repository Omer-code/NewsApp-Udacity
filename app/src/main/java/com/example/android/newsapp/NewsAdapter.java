package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public NewsAdapter(Context context, List<NewsItem> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        NewsItem currentNewsItem = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(currentNewsItem.getTitle());

        TextView sectionTextView = listItemView.findViewById(R.id.section);
        sectionTextView.setText(currentNewsItem.getSection());

        TextView authorTextView = listItemView.findViewById(R.id.author);
        if(currentNewsItem.getAuthorsCount()>1)
            authorTextView.setText("by "+currentNewsItem.getAuthor()+", and others");
        else
            authorTextView.setText("by "+currentNewsItem.getAuthor());

        TextView dateTextView = listItemView.findViewById(R.id.date);
        String str = currentNewsItem.getDate();
        String str2 = str.substring(0, str.indexOf("T"));
        dateTextView.setText(str2);

        return listItemView;
    }

}
