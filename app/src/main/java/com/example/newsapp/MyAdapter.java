package com.example.newsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewTitle;
        public TextView textViewContents;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.TextViewTitle);
            textViewContents = v.findViewById(R.id.TextViewContents);
            imageView = (SimpleDraweeView) v.findViewById(R.id.ImageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    // Activity Context를 가져다가 쓰면 메모리 누수가 발생한다. (일단은 쉽게 할 수 있는 부분이라서 사용)
    public MyAdapter(List<NewsData> myDataset, Context context) {
        mDataset = myDataset;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news = mDataset.get(position);

        holder.textViewTitle.setText(news.getTitle());

        String content = news.getContents();

        if(news.getContents() != "null" && content.length() > 0){
            holder.textViewContents.setText(content);
        } else {
            holder.textViewContents.setText("-");
        }

        Uri uri = Uri.parse(news.getUrlToImage());
        holder.imageView.setImageURI(uri);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
           //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }
}

