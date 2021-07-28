package com.example.wellbees_project.detailedSources

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetailedSource {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("articles")
    @Expose
    var articles: ArrayList<Article>? = null
}