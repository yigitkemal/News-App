package com.example.wellbees_project.retrofit

import com.example.wellbees_project.allSources.SourcesReply
import com.example.wellbees_project.detailedSources.DetailedSource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//SourcesdaoInterfaceim sayesinde retrofitten veri çekmemi sağlayacak urlnin oluşturulmasını sağlıyor ve bu url ile veri istenmesini gerçekleştiriyorum.
interface SourcesDAOInterface {
    //@GET("/v2/sources?country=us&apiKey=d8920f7f20be4311a9d4e2d76dc68139")
    @GET("/v2/sources")
    fun allSources(@Query("apiKey") api_key: String?): Call<SourcesReply?>

    // /v2/top-headlines?sources=bbc-news&apiKey=d8920f7f20be4311a9d4e2d76dc68139
    @GET("/v2/top-headlines")
    fun getSourceDetail(@Query("sources") sourcesId: String?,
                        @Query("apiKey") apiKey: String?): Call<DetailedSource?>
}