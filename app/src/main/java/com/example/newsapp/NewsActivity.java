package com.example.newsapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue queue;

    private String[] mDataset ={"1", "2", "3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        getNews();
        //1. 화면이 로딩 --> 뉴스 정보를 받아온다.
        //2. 정보 --> 어댑터에 넘겨준다.
        //3. 어댑터 --> 셋팅
    }

    //뉴스정보 받아오기 (JSON)
    //Reference volley document
    public void getNews(){
        //News API (South Korea)
        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=6b3b4723e2e94f4b98aee5ed716bb4c6";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrayArticles = jsonObject.getJSONArray("articles");

                            //response --> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                            for(int i = 0; i < arrayArticles.length(); i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContents(obj.getString("description"));
                                news.add(newsData);
                            }
                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this);
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

