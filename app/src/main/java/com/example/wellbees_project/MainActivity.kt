package com.example.wellbees_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.allSources.AllSourcesAdapter
import com.example.wellbees_project.allSources.Source
import com.example.wellbees_project.allSources.SourcesReply
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable
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
        val fab = findViewById<FloatingActionButtonExpandable>(R.id.fab)

        sourcesDAOInterface = ApiUtils.sourcesDaoInterface


        setupFab(fab,applicationContext)
        allSources(recyclerView)
    }

    fun allSources(recyclerView: RecyclerView) {
        val call = sourcesDAOInterface!!.allSources(API_KEY).enqueue(object : Callback<SourcesReply?> {
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


    private fun setupFab(fab: FloatingActionButtonExpandable, applicationContext: Context) {
        var fabController: Boolean = true

        fab.setOnClickListener {
            if (fabController == true){
                // eğer expand ise açıktır ve açık haline tıklanınca yeni bir sayfaya yönlenmesini sağlıyorum.
                fabController = false
                fab.expand()

                val intent = Intent(applicationContext, BookmarksActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)


            }else{
                fabController = true
                fab.collapse()
            }

        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}

