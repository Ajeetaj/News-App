package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerAdapter recyclerAdapter;
    private ArrayList<Articles> articlesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.RecyclerView);
        articlesArrayList=new ArrayList<>();
        recyclerAdapter=new RecyclerAdapter(articlesArrayList,MainActivity.this);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(recyclerAdapter);
        getNews();
        recyclerAdapter.notifyDataSetChanged();
    }
    private void getNews(){
        articlesArrayList.clear();
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=ccbdf4f6c5314319b6f5424e5f70c70d";
        String base_url="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
        Call<DataModelClass> call=retrofitAPI.getAllNews(url);
        call.enqueue(new Callback<DataModelClass>() {
            @Override
            public void onResponse(Call<DataModelClass> call, Response<DataModelClass> response) {
                DataModelClass dataModelClass=response.body();
                ArrayList<Articles> articles=dataModelClass.getArticles();
                for(int i=0;i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getContent(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getDescription(),
                            articles.get(i).getTitle()));
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DataModelClass> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}