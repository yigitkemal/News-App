package com.example.wellbees_project.allSources

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.DetailActivity
import com.example.wellbees_project.R
import com.example.wellbees_project.allSources.AllSourcesAdapter.AllSourcesViewHolder

class AllSourcesAdapter(private val sources: List<Source?>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<AllSourcesViewHolder>() {
    class AllSourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sourceLayout: LinearLayout
        var sourceTitle: TextView
        var sourceDescription: TextView
        var sourceData: TextView
        var sourceFav: TextView? = null

        init {
            sourceLayout = itemView.findViewById(R.id.source_layout)
            sourceTitle = itemView.findViewById(R.id.title)
            sourceData = itemView.findViewById(R.id.subtitle)
            sourceDescription = itemView.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSourcesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return AllSourcesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllSourcesViewHolder, position: Int) {
        holder.sourceTitle.text = sources!![position]!!.name
        holder.sourceDescription.text = sources[position]!!.description
        holder.sourceData.text = sources[position]!!.language
        holder.itemView.setOnClickListener {
            println("--------------------------" + sources[position]!!.ıd + "--------------------------")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("sourcesId", sources[position]!!.ıd) //Optional parameters
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return sources!!.size
    }
}