package com.example.mygallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context ctx;

    public RecyclerAdapter(ArrayList<Articles> articlesArrayList, Context ctx) {
        this.articlesArrayList = articlesArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles articles=articlesArrayList.get(position);
        holder.headingTV.setText(articles.getTitle());
        holder.bodyTV.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(articles.getUrl()));
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsIV;
        TextView headingTV,bodyTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headingTV=itemView.findViewById(R.id.NewsHeading);
            bodyTV=itemView.findViewById(R.id.NewsBody);
            newsIV=itemView.findViewById(R.id.NewsIV);
        }
    }
}
