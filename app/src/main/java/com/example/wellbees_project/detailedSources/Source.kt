package com.example.wellbees_project.detailedSources

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Source {
    @SerializedName("id")
    @Expose
    var ıd: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}