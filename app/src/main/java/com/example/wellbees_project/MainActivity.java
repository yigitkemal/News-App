package com.example.wellbees_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.wellbees_project.allSources.AllSourcesAdapter;
import com.example.wellbees_project.allSources.Source;
import com.example.wellbees_project.allSources.SourcesReply;
import com.example.wellbees_project.detailedSources.Article;
import com.example.wellbees_project.detailedSources.DetailedSource;

import java.util.List;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "d8920f7f20be4311a9d4e2d76dc68139";
    private final static String COUNTRY = "us";

    private SourcesDAOInterface sourcesDAOInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.source_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sourcesDAOInterface = ApiUtils.getSourcesDaoInterface();

        allSources(recyclerView);

    }


    public void allSources(RecyclerView recyclerView){
        sourcesDAOInterface.allSources(COUNTRY,API_KEY).enqueue(new Callback<SourcesReply>() {
            @Override
            public void onResponse(Call<SourcesReply> call, Response<SourcesReply> response) {

                List<Source> sourcesList = response.body().getSources();

                for (Source k:sourcesList) {
                    Log.e("**************", "****************");
                    Log.e("source id: ", k.getId());
                    Log.e("source id: ", k.getCountry());
                    Log.e("source id: ", k.getName());
                }

                recyclerView.setAdapter(new AllSourcesAdapter(sourcesList,R.layout.list_item,getApplicationContext()));


            }
            @Override
            public void onFailure(Call<SourcesReply> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }



}

