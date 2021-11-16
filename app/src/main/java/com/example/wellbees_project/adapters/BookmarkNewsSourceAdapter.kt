package com.example.wellbees_project.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.activity.DetailActivity
import com.example.wellbees_project.R
import com.example.wellbees_project.databinding.ListItemBinding
import com.example.wellbees_project.models.NewsSourceModel
import java.lang.Exception

class BookmarkNewsSourceAdapter (val newsSourceList: ArrayList<NewsSourceModel>, private val rowLayout: Int, private val context: Context?): RecyclerView.Adapter<BookmarkNewsSourceAdapter.BookmarkNewsSourceHolder>() {


    class BookmarkNewsSourceHolder (val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsSourceHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookmarkNewsSourceAdapter.BookmarkNewsSourceHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkNewsSourceHolder, position: Int) {

        holder.binding.title.text = newsSourceList!![position]!!.title
        holder.binding.description.text = newsSourceList!![position]!!.description
        holder.binding.countryFlag.text = newsSourceList!![position]!!.language!!.toFlagEmoji()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("sourcesId", newsSourceList[position]!!.urlId) //Optional parameters
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context!!.startActivity(intent)
        }

        val database = context?.openOrCreateDatabase("NewsSource", Context.MODE_PRIVATE, null)
        try {
            val difficultString = newsSourceList[position]!!.title!!.replace("'","''")

            var cursor = database!!.rawQuery("SELECT * FROM newssource WHERE title='${difficultString}' ", null)

            val newsTitle = cursor.getColumnIndex("title")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitle)

                Log.e("***", title)
                Log.e("***", newsSourceList[position]!!.title!!)

                if(title == (newsSourceList[position]!!.title!!)){
                    holder.binding.checkboxMain.isChecked = cursor.count > 0
                }
            }



            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }

        holder.binding.checkboxMain.setOnCheckedChangeListener{buttonView, isChecked ->
            val database = context!!.openOrCreateDatabase("NewsSource", AppCompatActivity.MODE_PRIVATE,null)
            if(isChecked){
                holder.binding.checkboxMain.isChecked = false
            }else{
                try {
                    val difficultString = newsSourceList[position]!!.title!!.replace("'","''")
                    database.execSQL("DELETE FROM newssource WHERE title ='${difficultString}'")
                }catch (e: Exception){
                    Log.e("ERROR", e.toString())
                }

            }

        }
    }


    fun String.toFlagEmoji(): String {

        if (this.length != 2) {
            return this
        }

        var countryCodeCaps = this.toUpperCase() // upper case is important because we are calculating offset

        if(countryCodeCaps.equals("EN")){
            countryCodeCaps = "GB"
        }else if(countryCodeCaps.equals("AR")){
            countryCodeCaps = "AE"
        }else if (countryCodeCaps.equals("İT")){
            countryCodeCaps = "IT"
        }else if (countryCodeCaps.equals("UD")){
            countryCodeCaps = "PK"
        }else if (countryCodeCaps.equals("HE")){
            countryCodeCaps = "IL"
        }else if (countryCodeCaps.equals("ZH")){
            countryCodeCaps = "CN"
        }

        val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

        // 2. It then checks if both characters are alphabet
        if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
            return this
        }


        println(String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter)) + "--------------------------------------")
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

    override fun getItemCount(): Int {
       return newsSourceList.size
    }

}