package com.example.wellbees_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.R
import com.example.wellbees_project.models.NewsDetailModel

class BookmarkNewsDetailAdapter(val newsDetailList: ArrayList<NewsDetailModel>, private val rowLayout: Int, private val context: Context?): RecyclerView.Adapter<BookmarkNewsDetailAdapter.BookmarkNewsDetailHolder>() {

    class BookmarkNewsDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var sourceLayout: LinearLayout
        var sourceTitle: TextView
        var sourceDescription: TextView
        var sourceContent: TextView
        var sourceDetailImage: ImageView
        var checkBoxDetail: CheckBox

        init {
            sourceLayout = itemView.findViewById(R.id.source_layout)
            sourceTitle = itemView.findViewById(R.id.title)
            sourceContent = itemView.findViewById(R.id.content)
            sourceDescription = itemView.findViewById(R.id.description)
            sourceDetailImage = itemView.findViewById(R.id.imageview_source_detail)
            checkBoxDetail = itemView.findViewById(R.id.checkbox_detail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsDetailHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout,parent,false)
        return BookmarkNewsDetailHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkNewsDetailHolder, position: Int) {
        holder.sourceTitle.text = newsDetailList.get(position).name


        holder.itemView.setOnClickListener{

        }

    }

    override fun getItemCount(): Int {
        return newsDetailList.size
    }
}