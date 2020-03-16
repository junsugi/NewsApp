package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView detailHeadline;
    private TextView detailContents;
    private ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datail_news);
        Intent intent = getIntent();

        detailHeadline = findViewById(R.id.detailHeadline);
        detailContents = findViewById(R.id.detailContents);
        detailImage = findViewById(R.id.detailImage);

        Object obj = intent.getSerializableExtra("NewsDetail");
        NewsData newsData = ((NewsData) obj);


        Log.d("TITLE", newsData.getTitle());
        Log.d("IMAGEURL", newsData.getUrlToImage());
        Log.d("CONTENTS", newsData.getContents());

        detailHeadline.setText(newsData.getTitle());
        detailContents.setText(newsData.getContents());

        Uri uri = Uri.parse(newsData.getUrlToImage());
        detailImage.setImageURI(uri);
    }

}
