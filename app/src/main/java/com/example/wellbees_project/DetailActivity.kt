package com.example.wellbees_project

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.detailedSources.DetailedSource
import com.example.wellbees_project.detailedSources.SourceDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private var sourcesDAOInterface: SourcesDAOInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val recyclerView = findViewById<View>(R.id.source_detail_recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val sourcesId = intent.getStringExtra("sourcesId")
        sourcesDAOInterface = ApiUtils.sourcesDaoInterface
        getSourceDetail(sourcesId, recyclerView)
    }

    fun getSourceDetail(sourcesId: String?, recyclerView: RecyclerView) {
        sourcesDAOInterface!!.getSourceDetail(sourcesId, API_KEY).enqueue(object : Callback<DetailedSource?> {
            override fun onResponse(call: Call<DetailedSource?>, response: Response<DetailedSource?>) {
                val articleList = response.body()!!.articles
                for (a in articleList!!) {
                    Log.e("**************", "****************")
                    Log.e("source id: ", a.title!!)
                    Log.e("source id: ", a.content!!)
                    Log.e("source id: ", a.url!!)
                }
                recyclerView.adapter = SourceDetailAdapter(articleList, R.layout.list_item_sources, applicationContext)
            }

            override fun onFailure(call: Call<DetailedSource?>, t: Throwable) {}
        })
    }

    companion object {
        private const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}