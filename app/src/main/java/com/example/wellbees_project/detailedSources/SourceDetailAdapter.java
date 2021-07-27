package com.example.wellbees_project.detailedSources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellbees_project.R;
import com.example.wellbees_project.allSources.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SourceDetailAdapter extends RecyclerView.Adapter<SourceDetailAdapter.SourceDetailViewHolder> {

    private List<Article> sources;
    private int rowLayout;
    private Context context;

    public static class SourceDetailViewHolder extends RecyclerView.ViewHolder{

        LinearLayout sourceLayout;
        TextView sourceTitle;
        TextView sourceDescription;
        TextView sourceData;
        ImageView sourceDetailImage;


        public SourceDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceLayout = itemView.findViewById(R.id.source_layout);
            sourceTitle = itemView.findViewById(R.id.title);
            sourceData = itemView.findViewById(R.id.subtitle);
            sourceDescription = itemView.findViewById(R.id.description);
            sourceDetailImage = itemView.findViewById(R.id.imageview_source_detail);
        }
    }

    public SourceDetailAdapter(List<Article> sources, int rowLayout, Context context) {
        this.sources = sources;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public SourceDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new SourceDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceDetailAdapter.SourceDetailViewHolder holder, int position) {
        holder.sourceTitle.setText(sources.get(position).getTitle());
        holder.sourceDescription.setText(sources.get(position).getContent());
        Picasso.get().load(sources.get(position).getUrlToImage()).into(holder.sourceDetailImage);
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }
}
