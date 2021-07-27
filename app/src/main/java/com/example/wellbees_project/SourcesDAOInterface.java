package com.example.wellbees_project;

import com.example.wellbees_project.allSources.SourcesReply;
import com.example.wellbees_project.detailedSources.DetailedSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SourcesDAOInterface {

    //@GET("/v2/sources?country=us&apiKey=d8920f7f20be4311a9d4e2d76dc68139")
    @GET("/v2/sources")
    Call<SourcesReply> allSources(@Query("country") String sourcesCountry,
                                  @Query("apiKey") String api_key);

    // /v2/top-headlines?sources=bbc-news&apiKey=d8920f7f20be4311a9d4e2d76dc68139
    @GET("/v2/top-headlines")
    Call<DetailedSource> getSourceDetail(@Query("sources") String sourcesId,
                                         @Query("apiKey")String apiKey);



}
