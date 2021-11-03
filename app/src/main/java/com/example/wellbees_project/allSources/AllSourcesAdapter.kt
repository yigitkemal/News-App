package com.example.wellbees_project.allSources

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
import com.example.wellbees_project.allSources.AllSourcesAdapter.AllSourcesViewHolder
import java.lang.Exception

class AllSourcesAdapter(private val sources: List<Source?>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<AllSourcesViewHolder>() {

    //haber kaynaklarımda kullanacağım elementlerin tanımlanmasını yapıyorum
    class AllSourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sourceLayout: LinearLayout
        var sourceTitle: TextView
        var sourceDescription: TextView
        var sourceData: TextView
        val checkBox: CheckBox

        init {
            //haber kaynaklarının bulunduğu ekrandaki elementleri xml dosyam ile ilişkilendiriyorum
            sourceLayout = itemView.findViewById(R.id.source_layout)
            sourceTitle = itemView.findViewById(R.id.title)
            sourceData = itemView.findViewById(R.id.countryFlag)
            sourceDescription = itemView.findViewById(R.id.description)
            checkBox = itemView.findViewById(R.id.checkbox_main)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSourcesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return AllSourcesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllSourcesViewHolder, position: Int) {
        //ilişkilendirmiş olduğum elementlerin üzerine verilerin atanmasını sağladım
        holder.sourceTitle.text = sources!![position]!!.name
        holder.sourceDescription.text = sources[position]!!.description
        //burada farklı bir yöntem izleyerek dillerin yazı olarak değil bayrak olarak gözükmesini sağladım
        holder.sourceData.text = sources!![position]!!.language!!.toFlagEmoji()

        // liste elemanıma tıklanma özelliğini ekledim
        holder.itemView.setOnClickListener {
            println("--------------------------" + sources[position]!!.ıd + "--------------------------")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("sourcesId", sources[position]!!.ıd) //Optional parameters
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        //veri tabanı yoksa oluşturulsun varsa açılsın diye aşağıdaki sorguyu çalıştırıyorum
        val database = context?.openOrCreateDatabase("NewsSource", Context.MODE_PRIVATE, null)
        try {
            //çekmiş olduğum veri içinde bulunan tırnak işareti gibi veriler sorgumu engelliyor
            // bu yüzden tırnak işaretlerinin de okunmasını sağlayabilmek için onları değiştirip başka bir string içinde tutuyorum.
            val difficultString = sources[position]!!.name!!.replace("'","''")

            var cursor = database!!.rawQuery("SELECT * FROM newssource WHERE title='${difficultString}' ", null)

            //çekmiş olduğum verinin title'ını kenara ayırıyorum
            val newsTitle = cursor.getColumnIndex("title")

            while (cursor.moveToNext()){
                val title = cursor.getString(newsTitle)

                Log.e("***", title)
                Log.e("***", sources[position]!!.name!!)

                //eğer title veritabanımdaki herhangi bir veri ile eşitse bookmarkın işaretli olmasını sağlıyorum
                if(title == (sources[position]!!.name!!)){
                    holder.checkBox.isChecked = cursor.count > 0
                }
            }



            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }


        //burada bookmark olarak adlandırmış olduğum checkboxlarımın açılıp kapanmasını dinliyorum.
        holder.checkBox.setOnCheckedChangeListener{buttonView, isChecked ->
            val database = context.openOrCreateDatabase("NewsSource", AppCompatActivity.MODE_PRIVATE,null)

            // eğer bookmark işaretli değilken tıklamış isem bu alana girip o verinin veritabanıma kaydolmasını sağlıyorum
            if(isChecked){
                try {
                    database.execSQL("CREATE TABLE IF NOT EXISTS newssource(id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, language VARCHAR , urlId VARCHAR)")

                    val sqlString = "INSERT INTO newssource (title, description, language, urlId) VALUES (?, ?, ?, ?)"
                    val statement = database.compileStatement(sqlString)

                    statement.bindString(1,sources!![position]!!.name)
                    statement.bindString(2,sources!![position]!!.description)
                    statement.bindString(3,sources!![position]!!.language)
                    statement.bindString(4,sources!![position]!!.ıd)

                    statement.execute()
                }catch (e: Exception){
                    Log.e("ERROR", e.toString())
                }
            }else{
                try {
                    //eğer bookmark işaretliyken tıklamış isem o verinin veritabanından silinmesini sağlıyorum
                    val difficultString = sources[position]!!.name!!.replace("'","''")
                    database.execSQL("DELETE FROM newssource WHERE title ='${difficultString}'")
                }catch (e: Exception){
                    Log.e("ERROR", e.toString())
                }

            }

        }

    }



    //bu fonksiyon sayesinde dil kodlarımı ülke bayrağı emojisine çeviriyorum
    fun String.toFlagEmoji(): String {

        if (this.length != 2) {
            return this
        }

        var countryCodeCaps = this.toUpperCase()

        //dil kodları ülke kodları ile uyuşmuyordu ve bayraklar için ülke koduna ihtiyacım vardı,
        // dil kodlarını ilgili ülke koduna aşağıdaki if else ile çevirdim.
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

        if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
            return this
        }

        println(String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter)) + "--------------------------------------")
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

    override fun getItemCount(): Int {
        return sources!!.size
    }



}