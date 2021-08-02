package com.example.wellbees_project.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.R
import com.example.wellbees_project.adapters.BookmarkNewsDetailAdapter
import com.example.wellbees_project.models.NewsDetailModel
import java.lang.Exception

class NewsDetailFragment : Fragment() {

    private lateinit var newsDetailList: ArrayList<NewsDetailModel>
    private lateinit var bookmarkAdapter: BookmarkNewsDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsDetailList = ArrayList()
        bookmarkAdapter = BookmarkNewsDetailAdapter(newsDetailList,R.layout.list_item_sources,context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_news_detail, container, false)
        var recyclerView = rootView.findViewById<RecyclerView>(R.id.recycylerview_news_detail_fragment)


        // kaydedilmiş haberler için recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = bookmarkAdapter

        //burada kaydedilmiş haber değerlerimi sqlden çekiyorum
        getNewsDetailSqlData()


        return rootView
    }

    //sql üzerinden kayıtlı haber kaynaklarının çekilme işlemini gerçekleştiriyorum
    private fun getNewsDetailSqlData() {
        try {
            //verileri NewsDetails tablsoundan çekeceğimi belirtiyorum
            val database = activity?.openOrCreateDatabase("NewsDetails", MODE_PRIVATE, null)

            //sorgumu bir cursor içinde tutup kontrolünü ve performansını artırıyorum
            var cursor = database!!.rawQuery("SELECT * FROM newsdetails", null)
            val idIx = cursor.getColumnIndex("id")
            val newsTitleIx = cursor.getColumnIndex("title")
            val newsDescription = cursor.getColumnIndex("description")
            val newsContent = cursor.getColumnIndex("content")
            val newsUrl = cursor.getColumnIndex("newsUrl")
            val newsPhotoUrl = cursor.getColumnIndex("photoUrl")

            //cursor içinde bulunan verilerin hepsinin çekilmesi için onları bir while döngüsü ile çekiyorum ve oluşturmuş olduğum modelde tutuyorum.
            while (cursor.moveToNext()) {
                val id = cursor.getInt(idIx)
                val title = cursor.getString(newsTitleIx)
                val description = cursor.getString(newsDescription)
                val content = cursor.getString(newsContent)
                val url = cursor.getString(newsUrl)
                val urlToImage = cursor.getString(newsPhotoUrl)

                val newsDetail = NewsDetailModel(id, title, description, content, urlToImage, url)
                Log.e("****-********", title)
                newsDetailList.add(newsDetail)
            }

            //yük olmaması için cursorum ile işim bitince kapatıyorum
            cursor.close()

            //herhangi bir veri değişikliği olup olmadığını dinliyorum
            bookmarkAdapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}