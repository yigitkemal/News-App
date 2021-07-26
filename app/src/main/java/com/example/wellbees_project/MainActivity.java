package com.example.wellbees_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SourcesDAOInterface sourcesDAOInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourcesDAOInterface = ApiUtils.getSourcesDaoInterface();

        allSources();

    }


    public void allSources(){
        sourcesDAOInterface.allSources().enqueue(new Callback<SourcesReply>() {
            @Override
            public void onResponse(Call<SourcesReply> call, Response<SourcesReply> response) {

                List<Source> sourcesList = response.body().getSources();

                for (Source k:sourcesList) {
                    Log.e("**************", "****************");
                    Log.e("source id: ", k.getId());
                    Log.e("source id: ", k.getCountry());
                    Log.e("source id: ", k.getName());

                }



            }

            @Override
            public void onFailure(Call<SourcesReply> call, Throwable t) {

            }
        });
    }

}