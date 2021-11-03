package com.example.wellbees_project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.retrofit.ApiUtils
import com.example.wellbees_project.R
import com.example.wellbees_project.retrofit.SourcesDAOInterface
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

        //recyclerviewin xml dosyası ile bağlantısının sağlandığı yer
        recyclerView = findViewById<View>(R.id.source_detail_recyclerview) as RecyclerView

        //floatingactionbuttonımın xml dosyası ile bağlnantısının sağlandığı yer
        val fab = findViewById<FloatingActionButtonExpandable>(R.id.fab)

        recyclerView.layoutManager = LinearLayoutManager(this)

        //bu alan sayesinde bir önceki activityde çekilen verilerden sourcesIdyi DetailActivityye taşıyorum. Bunun sayesinde seçilen haber kaynağının getirilmesini sağlayacağım.
        val sourcesId = intent.getStringExtra("sourcesId")
        sourcesDAOInterface = ApiUtils.sourcesDaoInterface


        setupFab(fab, applicationContext)
        getSourceDetail(sourcesId, recyclerView)

    }

    // buradaki fonksiyonda retrofit ile bir önceki activitymden aldığım sourcesId ile ilgili haber kaynağındaki haberleri çekiyorum
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


    //main activitydeki floatingactionbutton fonksiyonunun birebir aynısı.
    private fun setupFab(fab: FloatingActionButtonExpandable, applicationContext: Context) {
        var fabController: Boolean = true

        fab.setOnClickListener {
            if (fabController == true) {
                // eğer expand ise açıktır ve açık haline tıklanınca yeni bir sayfaya yönlenmesini sağlıyorum.
                fabController = false
                fab.expand()

                val intent = Intent(applicationContext, BookmarksActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)


            } else {
                fabController = true
                fab.collapse()
            }

        }
    }


    //burada detail activity im içerisinde arama sağlıyorum
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Search..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()) {
                        //listemin aslında her seferde temizlenmesini sağlıyorum, displaylist gözüken listem
                        displayList.clear()

                        //aranan veriyi search nesnesine atıyorum
                        val search = newText!!.toLowerCase(Locale.getDefault())

                        //search nesnesi ile genel çektiğim liste üzerinde arama yapıyorum ve
                        // eğer eşit ise articlelistimdeki eşleşen veriyi boşalttığım displayliste ekliyorum
                        articleList.forEach {
                            if (it.title!!.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it);
                            }
                        }

                        //verilerde değişiklik olmuş ise bunu recyclerviewime bildiriyorum
                        recyclerView.adapter!!.notifyDataSetChanged()

                    } else {
                        //herhangi bir değişiklik yok ise displaylistimi temizleyip tüm articlelisti içine atıyorum.
                        displayList.clear()
                        displayList.addAll(articleList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val API_KEY = "d8920f7f20be4311a9d4e2d76dc68139"
        private const val COUNTRY = "us"
    }
}