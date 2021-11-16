package com.example.wellbees_project.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.R
import com.example.wellbees_project.adapters.BookmarkNewsSourceAdapter
import com.example.wellbees_project.databinding.FragmentSourceBinding
import com.example.wellbees_project.models.NewsSourceModel
import java.lang.Exception


class SourceFragment : Fragment() {

    private lateinit var binding: FragmentSourceBinding

    private lateinit var sourceDetailList: ArrayList<NewsSourceModel>
    private lateinit var bookmarkAdapter: BookmarkNewsSourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sourceDetailList = ArrayList()
        bookmarkAdapter = BookmarkNewsSourceAdapter(sourceDetailList,R.layout.list_item,context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSourceBinding.inflate(inflater,container,false)
        val rootView = binding.root

        //burada fragmentimin içerisinde bulunan recyclerviewe atama yapıyorum
        var recyclerView = binding.recycylerviewNewsSourceFragment

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
            //verileri NewsSource tablsoundan çekeceğimi belirtiyorum
            val database = activity?.openOrCreateDatabase("NewsSource", Context.MODE_PRIVATE, null)

            //sorgumu bir cursor içinde tutup kontrolünü ve performansını artırıyorum
            var cursor = database!!.rawQuery("SELECT * FROM newssource", null)
            val idIx = cursor.getColumnIndex("id")
            val newsTitleIx = cursor.getColumnIndex("title")
            val newsDescription = cursor.getColumnIndex("description")
            val newsContent = cursor.getColumnIndex("language")
            val newsUrlId = cursor.getColumnIndex("urlId")

            //cursor içinde bulunan verilerin hepsinin çekilmesi için onları bir while döngüsü ile çekiyorum ve oluşturmuş olduğum modelde tutuyorum.
            while (cursor.moveToNext()) {
                val id = cursor.getInt(idIx)
                val title = cursor.getString(newsTitleIx)
                val description = cursor.getString(newsDescription)
                val content = cursor.getString(newsContent)
                val urlId = cursor.getString(newsUrlId)

                val newsSource = NewsSourceModel(id, title, description, content, urlId)
                sourceDetailList.add(newsSource)
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