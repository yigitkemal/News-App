package com.example.wellbees_project;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SourcesDAOInterface {


    @GET("/v2/sources?country=us&apiKey=d8920f7f20be4311a9d4e2d76dc68139")
    Call<SourcesReply> allSources();

}
