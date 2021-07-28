package com.example.wellbees_project.allSources

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SourcesReply {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("sources")
    @Expose
    var sources: List<Source>? = null
}