package com.example.wellbees_project.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.R
import com.example.wellbees_project.databinding.ListItemSourcesBinding
import com.example.wellbees_project.models.NewsDetailModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class BookmarkNewsDetailAdapter(val newsDetailList: ArrayList<NewsDetailModel>, private val rowLayout: Int, private val context: Context?): RecyclerView.Adapter<BookmarkNewsDetailAdapter.BookmarkNewsDetailHolder>() {

    class BookmarkNewsDetailHolder(val binding: ListItemSourcesBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsDetailHolder {
        val binding = ListItemSourcesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookmarkNewsDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkNewsDetailHolder, position: Int) {
        holder.binding.title.text = newsDetailList.get(position).title
        holder.binding.description.text = newsDetailList.get(position).description
        holder.binding.content.text = newsDetailList.get(position).content
        Picasso.get().load(newsDetailList.get(position)!!.urlToImage).into(holder.binding.imageviewSourceDetail)

        holder.itemView.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsDetailList[position]!!.url))
            browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context!!.startActivity(browserIntent)
        }

        val database = context?.openOrCreateDatabase("NewsDetails", Context.MODE_PRIVATE, null)
        try {
            val difficultString = newsDetailList[position]!!.title!!.replace("'","''")

            var cursor = database!!.rawQuery("SELECT * FROM newsdetails WHERE title='${difficultString}' ", null)

            val newsTitle = cursor.getColumnIndex("title")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitle)

                Log.e("***", title)
                Log.e("***", newsDetailList[position]!!.title!!)

                if(title == (newsDetailList[position]!!.title!!)){
                    holder.binding.checkboxDetail.isChecked = cursor.count > 0
                }
            }



            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }

        holder.binding.checkboxDetail.setOnCheckedChangeListener{buttonView, isChecked ->
            val database = context!!.openOrCreateDatabase("NewsDetails", AppCompatActivity.MODE_PRIVATE,null)
            if(isChecked){
                holder.binding.checkboxDetail.isChecked = false
            }else{
                try {
                    val difficultString = newsDetailList[position]!!.title!!.replace("'","''")
                    database.execSQL("DELETE FROM newsdetails WHERE title ='${difficultString}'")
                }catch (e: Exception){
                    Log.e("ERROR", e.toString())
                }

            }

        }


    }




    override fun getItemCount(): Int {
        return newsDetailList.size
    }
}