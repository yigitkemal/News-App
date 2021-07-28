package com.example.wellbees_project.detailedSources

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wellbees_project.R
import com.example.wellbees_project.detailedSources.SourceDetailAdapter.SourceDetailViewHolder
import com.squareup.picasso.Picasso


class SourceDetailAdapter(private val sources: List<Article?>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<SourceDetailViewHolder>() {
    class SourceDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sourceLayout: LinearLayout
        var sourceTitle: TextView
        var sourceDescription: TextView
        var sourceSubtitle: TextView
        var sourceDetailImage: ImageView

        init {
            sourceLayout = itemView.findViewById(R.id.source_layout)
            sourceTitle = itemView.findViewById(R.id.title)
            sourceSubtitle = itemView.findViewById(R.id.subtitle)
            sourceDescription = itemView.findViewById(R.id.description)
            sourceDetailImage = itemView.findViewById(R.id.imageview_source_detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return SourceDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceDetailViewHolder, position: Int) {
        holder.sourceTitle.text = sources!![position]!!.title
        holder.sourceDescription.text = sources[position]!!.description
        holder.sourceSubtitle.text = sources[position]!!.content
        Picasso.get().load(sources[position]!!.urlToImage).into(holder.sourceDetailImage)

        holder.itemView.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sources[position]!!.url))
            browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return sources!!.size
    }
}