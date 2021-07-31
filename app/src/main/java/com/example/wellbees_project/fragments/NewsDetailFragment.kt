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


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = bookmarkAdapter

        getNewsDetailSqlData()


        return rootView
    }

    private fun getNewsDetailSqlData() {
        try {
            val database = activity?.openOrCreateDatabase("NewsDetails", MODE_PRIVATE, null)

            var cursor = database!!.rawQuery("SELECT * FROM newsdetails", null)
            val idIx = cursor.getColumnIndex("id")
            val newsTitleIx = cursor.getColumnIndex("title")
            val newsDescription = cursor.getColumnIndex("description")
            val newsContent = cursor.getColumnIndex("content")
            val newsUrl = cursor.getColumnIndex("newsUrl")
            val newsPhotoUrl = cursor.getColumnIndex("photoUrl")

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

            cursor.close()
            bookmarkAdapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}