package com.example.movieapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.Activities.DetailActivity;
import com.example.movieapplication.Domains.Film;
import com.example.movieapplication.R;

import java.util.ArrayList;

public class FavoritesAdapter  extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private ArrayList<Film> items;

    Context context;
    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item,parent,false);
        return new ViewHolder(inflate);

    }
    public FavoritesAdapter(ArrayList<Film> items) {this.items = items;}
    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv1.setText(items.get(position).getTitle());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(30));
        Glide.with(context)
                .load(items.get(position).getPoster())
                .apply(requestOptions)
                .into(holder.mpic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        ImageView mpic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mpic=itemView.findViewById(R.id.imageViewfavmovie);
            tv1=itemView.findViewById(R.id.moviie_name);
            tv2=itemView.findViewById(R.id.info);

        }
    }


}
