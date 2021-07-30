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

        try {
            val database = activity?.openOrCreateDatabase("NewsDetails", MODE_PRIVATE,null)

            var cursor = database!!.rawQuery("SELECT * FROM newsdetails",null)
            val newsTitleIx = cursor.getColumnIndex("title")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitleIx)
                val id = cursor.getInt(idIx)
                val newsDetail = NewsDetailModel(title, id)
                Log.e("****-********", title)
                newsDetailList.add(newsDetail)
            }

            cursor.close()
            bookmarkAdapter.notifyDataSetChanged()

        }catch (e: Exception){
            e.printStackTrace()
        }


        return rootView
    }
}