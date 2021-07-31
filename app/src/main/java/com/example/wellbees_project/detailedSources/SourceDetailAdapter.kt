package com.example.wellbees_project.detailedSources

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
import com.example.wellbees_project.detailedSources.SourceDetailAdapter.SourceDetailViewHolder
import com.example.wellbees_project.models.NewsDetailModel
import com.squareup.picasso.Picasso
import java.lang.Exception


class SourceDetailAdapter(private val sources: List<Article?>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<SourceDetailViewHolder>() {
    class SourceDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return SourceDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceDetailViewHolder, position: Int) {
        holder.sourceTitle.text = sources!![position]!!.title
        holder.sourceDescription.text = sources[position]!!.description
        holder.sourceContent.text = sources[position]!!.content
        Picasso.get().load(sources[position]!!.urlToImage).into(holder.sourceDetailImage)


        holder.itemView.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sources[position]!!.url))
            browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(browserIntent)
        }

        val database = context?.openOrCreateDatabase("NewsDetails", Context.MODE_PRIVATE, null)
        try {
            val difficultString = sources[position]!!.title!!.replace("'","''")

            var cursor = database!!.rawQuery("SELECT * FROM newsdetails WHERE title='${difficultString}' ", null)

            val newsTitle = cursor.getColumnIndex("title")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitle)

                Log.e("***", title)
                Log.e("***", sources[position]!!.title!!)

                if(title == (sources[position]!!.title!!)){
                    holder.checkBoxDetail.isChecked = cursor.count > 0
                }
            }



            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }

        holder.checkBoxDetail.setOnCheckedChangeListener{buttonView, isChecked ->
            val database = context.openOrCreateDatabase("NewsDetails", AppCompatActivity.MODE_PRIVATE,null)
            if(isChecked){
                try {
                    database.execSQL("CREATE TABLE IF NOT EXISTS newsdetails(id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, content VARCHAR, newsUrl VARCHAR, photoUrl VARCHAR)")

                    val sqlString = "INSERT INTO newsdetails (title, description, content, newsUrl, photoUrl) VALUES (?, ?, ?, ?, ?)"
                    val statement = database.compileStatement(sqlString)
                    statement.bindString(1,sources!![position]!!.title)
                    statement.bindString(2,sources!![position]!!.description)
                    statement.bindString(3,sources!![position]!!.content)
                    statement.bindString(4,sources!![position]!!.url)
                    statement.bindString(5,sources!![position]!!.urlToImage)

                    statement.execute()
                }catch (e: Exception){
                    Log.e("ERROR", e.toString())
                }
            }else{
               try {
                   val difficultString = sources[position]!!.title!!.replace("'","''")
                   database.execSQL("DELETE FROM newsdetails WHERE title ='${difficultString}'")
               }catch (e:Exception){
                   Log.e("ERROR", e.toString())
               }

            }

        }

    }

    override fun getItemCount(): Int {
        return sources!!.size
    }
}