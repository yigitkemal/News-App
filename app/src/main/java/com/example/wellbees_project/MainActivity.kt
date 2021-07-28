package com.example.wellbees_project

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.allSources.AllSourcesAdapter
import com.example.wellbees_project.allSources.Source
import com.example.wellbees_project.allSources.SourcesReply
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var sourcesDAOInterface: SourcesDAOInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<View>(R.id.source_recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        sourcesDAOInterface = ApiUtils.sourcesDaoInterface



        allSources(recyclerView)
    }

    fun allSources(recyclerView: RecyclerView) {
        val call = sourcesDAOInterface!!.allSources(COUNTRY, API_KEY).enqueue(object : Callback<SourcesReply?> {
            override fun onResponse(call: Call<SourcesReply?>, response: Response<SourcesReply?>) {

               var sourceList = response.body()!!.sources

                for(a in sourceList!!) {
                    Log.e("**************", "****************")
                    Log.e("source id: ", a.ıd!!)
                    Log.e("source category: ", a.language!!)
                    Log.e("source id: ", a.description!!)
                }

                recyclerView.adapter = AllSourcesAdapter(sourceList,R.layout.list_item,applicationContext)
            }

            override fun onFailure(call: Call<SourcesReply?>, t: Throwable) {
                Log.e("****************", "hata ----------------")
            }
        } )

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}

