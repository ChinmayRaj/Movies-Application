package com.example.movieapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.Domains.Cast;
import com.example.movieapplication.R;

import java.util.ArrayList;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.Viewholder> {
    ArrayList<Cast>casts;
    Context context;
    @NonNull
    @Override
    public CastListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_actors,parent,false);

        return new Viewholder(inflate);
    }

    public CastListAdapter(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    @Override
    public void onBindViewHolder(@NonNull CastListAdapter.Viewholder holder, int position) {
        Glide.with(context)
                .load(casts.get(position).getPicUrl())
                .into(holder.pic);
        holder.name.setText(casts.get(position).getActor());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView name;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pic=itemView.findViewById(R.id.itemimage);
            name=itemView.findViewById(R.id.nametxt);
        }
    }
}
