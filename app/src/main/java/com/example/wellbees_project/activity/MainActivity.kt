package com.example.wellbees_project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.retrofit.ApiUtils
import com.example.wellbees_project.R
import com.example.wellbees_project.retrofit.SourcesDAOInterface
import com.example.wellbees_project.allSources.AllSourcesAdapter
import com.example.wellbees_project.allSources.SourcesReply
import com.example.wellbees_project.databinding.ActivityMainBinding
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var sourcesDAOInterface: SourcesDAOInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val recyclerView = binding.sourceRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        val fab = binding.fab

        sourcesDAOInterface = ApiUtils.sourcesDaoInterface


        setupFab(fab,applicationContext)
        allSources(recyclerView)
    }

    fun allSources(recyclerView: RecyclerView) {

        //burada retrofit ile tüm haber kaynaklarımı çekiyorum. Daha sonra bunları liste olarak recyclerviewime göndererek uygulama içerisinde gözükmesini sağlıyorum.
        val call = sourcesDAOInterface!!.allSources(API_KEY).enqueue(object : Callback<SourcesReply?> {
            override fun onResponse(call: Call<SourcesReply?>, response: Response<SourcesReply?>) {

               var sourceList = response.body()!!.sources

                for(a in sourceList!!) {
                    Log.e("**************", "****************")
                    Log.e("source id: ", a.ıd!!)
                    Log.e("source category: ", a.language!!)
                    Log.e("source id: ", a.description!!)
                }

                recyclerView.adapter = AllSourcesAdapter(sourceList, R.layout.list_item,applicationContext)
            }

            override fun onFailure(call: Call<SourcesReply?>, t: Throwable) {
                Log.e("****************", "hata ----------------")
            }
        } )

    }


    //Burada açılır kapanır floatingactionbuttonumın açılıp kapanma ayarlarını ve beni yönlendireceği sayfayı ayarlıyorum.
    private fun setupFab(fab: FloatingActionButtonExpandable, applicationContext: Context) {
        var fabController: Boolean = true

        fab.setOnClickListener {
            if (fabController == true){
                // eğer expand ise açıktır ve açık haline tıklanınca yeni bir sayfaya yönlenmesini sağlıyorum.
                fabController = false
                fab.expand()

                //Buradaki intent sayesinde bookmarks activityye geçiş sağlıyorum.
                val intent = Intent(applicationContext, BookmarksActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)


            }else{
                fabController = true
                fab.collapse()
            }

        }
    }

    //retrofit ile veri çekerken kullandığım api key gibi sabit verileri burada tutuyorum.
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}

