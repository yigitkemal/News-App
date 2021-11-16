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
import com.example.wellbees_project.databinding.ListItemSourcesBinding
import com.example.wellbees_project.detailedSources.SourceDetailAdapter.SourceDetailViewHolder
import com.example.wellbees_project.models.NewsDetailModel
import com.squareup.picasso.Picasso
import java.lang.Exception


class SourceDetailAdapter(private val sources: List<Article?>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<SourceDetailViewHolder>() {

    class SourceDetailViewHolder(val binding: ListItemSourcesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceDetailViewHolder {
        val binding = ListItemSourcesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SourceDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SourceDetailViewHolder, position: Int) {
        //ilişkilendirmiş olduğum elementlerin üzerine verilerin atanmasını sağladım
        holder.binding.title.text = sources!![position]!!.title
        holder.binding.description.text = sources[position]!!.description
        holder.binding.content.text = sources[position]!!.content
        Picasso.get().load(sources[position]!!.urlToImage).into(holder.binding.imageviewSourceDetail)


        // liste elemanıma tıklanma özelliğini ekledim
        holder.itemView.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sources[position]!!.url))
            browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(browserIntent)
        }

        //veri tabanı yoksa oluşturulsun varsa açılsın diye aşağıdaki sorguyu çalıştırıyorum
        val database = context?.openOrCreateDatabase("NewsDetails", Context.MODE_PRIVATE, null)
        try {
            //çekmiş olduğum veri içinde bulunan tırnak işareti gibi veriler sorgumu engelliyor
                // bu yüzden tırnak işaretlerinin de okunmasını sağlayabilmek için onları değiştirip başka bir string içinde tutuyorum.
            val difficultString = sources[position]!!.title!!.replace("'","''")

            var cursor = database!!.rawQuery("SELECT * FROM newsdetails WHERE title='${difficultString}' ", null)

            //çekmiş olduğum verinin title'ını kenara ayırıyorum
            val newsTitle = cursor.getColumnIndex("title")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitle)

                Log.e("***", title)
                Log.e("***", sources[position]!!.title!!)

                //eğer title veritabanımdaki herhangi bir veri ile eşitse bookmarkın işaretli olmasını sağlıyorum
                if(title == (sources[position]!!.title!!)){
                    holder.binding.checkboxDetail.isChecked = cursor.count > 0
                }
            }



            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }

        //burada bookmark olarak adlandırmış olduğum checkboxlarımın açılıp kapanmasını dinliyorum.
        holder.binding.checkboxDetail.setOnCheckedChangeListener{buttonView, isChecked ->
            //databaseimi açıyorum
            val database = context.openOrCreateDatabase("NewsDetails", AppCompatActivity.MODE_PRIVATE,null)

            // eğer bookmark işaretli değilken tıklamış isem bu alana girip o verinin veritabanıma kaydolmasını sağlıyorum
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
                //eğer bookmark işaretliyken tıklamış isem o verinin veritabanından silinmesini sağlıyorum
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