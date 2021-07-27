package com.example.wellbees_project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellbees_project.allSources.AllSourcesAdapter;
import com.example.wellbees_project.detailedSources.Article;
import com.example.wellbees_project.detailedSources.DetailedSource;
import com.example.wellbees_project.detailedSources.SourceDetailAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private SourcesDAOInterface sourcesDAOInterface;

    private final static String API_KEY = "d8920f7f20be4311a9d4e2d76dc68139";
    private final static String COUNTRY = "us";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.source_detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String sourcesId = getIntent().getStringExtra("sourcesId");

        sourcesDAOInterface = ApiUtils.getSourcesDaoInterface();

        getSourceDetail(sourcesId,recyclerView);

    }

    public void getSourceDetail(String sourcesId, RecyclerView recyclerView){
        sourcesDAOInterface.getSourceDetail(sourcesId,API_KEY).enqueue(new Callback<DetailedSource>() {
            @Override
            public void onResponse(Call<DetailedSource> call, Response<DetailedSource> response) {

                List<Article> articleList = response.body().getArticles();

                for (Article a:articleList) {
                    Log.e("**************", "****************");
                    Log.e("source id: ", a.getTitle());
                    Log.e("source id: ", a.getContent());
                    Log.e("source id: ", a.getUrl());
                }

                recyclerView.setAdapter(new SourceDetailAdapter(articleList,R.layout.list_item_sources,getApplicationContext()));

            }

            @Override
            public void onFailure(Call<DetailedSource> call, Throwable t) {

            }
        });
    }

}
