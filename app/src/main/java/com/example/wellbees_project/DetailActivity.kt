package com.example.wellbees_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.detailedSources.Article
import com.example.wellbees_project.detailedSources.DetailedSource
import com.example.wellbees_project.detailedSources.SourceDetailAdapter
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {
    private var sourcesDAOInterface: SourcesDAOInterface? = null

    val displayList = ArrayList<Article>()
    var articleList = ArrayList<Article>()
    lateinit var recyclerView: RecyclerView


   // val recyclerView = findViewById<View>(R.id.source_detail_recyclerview) as RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        recyclerView = findViewById<View>(R.id.source_detail_recyclerview) as RecyclerView
        val fab = findViewById<FloatingActionButtonExpandable>(R.id.fab)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val sourcesId = intent.getStringExtra("sourcesId")
        sourcesDAOInterface = ApiUtils.sourcesDaoInterface

        setupFab(fab,applicationContext)
        getSourceDetail(sourcesId, recyclerView)
    }

    fun getSourceDetail(sourcesId: String?, recyclerView: RecyclerView) {
        sourcesDAOInterface!!.getSourceDetail(sourcesId, API_KEY).enqueue(object : Callback<DetailedSource?> {
            override fun onResponse(call: Call<DetailedSource?>, response: Response<DetailedSource?>) {
                articleList = response.body()!!.articles!!


                displayList.addAll(articleList)

                recyclerView.adapter = SourceDetailAdapter(displayList, R.layout.list_item_sources, applicationContext)
            }

            override fun onFailure(call: Call<DetailedSource?>, t: Throwable) {}
        })
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



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Search..."

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                   return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        displayList.clear()
                        val search = newText!!.toLowerCase(Locale.getDefault())

                        articleList.forEach{
                            if(it.title!!.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it);
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()

                    }else{
                        displayList.clear()
                        displayList.addAll(articleList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            } )
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}