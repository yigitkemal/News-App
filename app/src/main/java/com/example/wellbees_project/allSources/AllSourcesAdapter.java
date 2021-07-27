package com.example.wellbees_project.allSources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellbees_project.DetailActivity;
import com.example.wellbees_project.MainActivity;
import com.example.wellbees_project.R;

import java.util.List;


public class AllSourcesAdapter extends RecyclerView.Adapter<AllSourcesAdapter.AllSourcesViewHolder> {


    private List<Source> sources;
    private int rowLayout;
    private Context context;


    public static class AllSourcesViewHolder extends RecyclerView.ViewHolder {

        LinearLayout sourceLayout;
        TextView sourceTitle;
        TextView sourceDescription;
        TextView sourceData;
        TextView sourceFav;

        public AllSourcesViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceLayout = itemView.findViewById(R.id.source_layout);
            sourceTitle = itemView.findViewById(R.id.title);
            sourceData = itemView.findViewById(R.id.subtitle);
            sourceDescription = itemView.findViewById(R.id.description);
        }

    }

    public AllSourcesAdapter(List<Source> sources, int rowLayout, Context context){
        this.sources = sources;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public AllSourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new AllSourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSourcesViewHolder holder, int position) {
        holder.sourceTitle.setText(sources.get(position).getName());
        holder.sourceDescription.setText(sources.get(position).getDescription());
        holder.sourceData.setText(sources.get(position).getLanguage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--------------------------"+sources.get(position).getId() +"--------------------------");
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("sourcesId", sources.get(position).getId()); //Optional parameters
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }




}
