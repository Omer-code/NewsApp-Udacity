package com.example.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {
    final private String baseNewsURL = "https://content.guardianapis.com/search";
    private NewsAdapter adapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyTextView);

        adapter = new NewsAdapter(this, new ArrayList<NewsItem>());
        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem currentNewsItem = adapter.getItem(position);

                Uri newsItemUri = Uri.parse(currentNewsItem.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsItemUri);
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(0, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_circle);
            loadingIndicator.setVisibility(View.GONE);

            emptyTextView.setText("No Internet Connection!");
        }
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        String url= buildUri();
        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> news) {
        View loadingIndicator = findViewById(R.id.loading_circle);
        loadingIndicator.setVisibility(View.GONE);

        emptyTextView.setText("No News Found");

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        adapter.clear();
    }

    public String buildUri(){
        Uri baseUri = Uri.parse(baseNewsURL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api-key","test");
        uriBuilder.appendQueryParameter("show-tags","contributor");
        String url = uriBuilder.toString();
        Log.e("URL TEST",url);
        return url;
    }
}