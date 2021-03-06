package com.example.wellbees_project.retrofit

object ApiUtils {
    const val BASE_URL = "https://newsapi.org"
    val sourcesDaoInterface: SourcesDAOInterface
        get() = RetrofitClient.getClient(BASE_URL)!!.create(SourcesDAOInterface::class.java)
}