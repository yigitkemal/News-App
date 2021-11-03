package com.example.wellbees_project.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //burada retrofit objesininin oluşmasını ve hangi ana url kaynağını kullanacağını ayarlıyorum.
    private var retrofit: Retrofit? = null
    fun getClient(baseURL: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}