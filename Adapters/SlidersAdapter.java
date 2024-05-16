package com.example.movieapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.Domains.SliderItems;
import com.example.movieapplication.R;

import java.util.List;

public class SlidersAdapter extends RecyclerView.Adapter<SlidersAdapter.SliderViewHolder>
{
private List<SliderItems> sliderItems;
private ViewPager2 viewPager2;
private Context context;
private Runnable  runnable=new Runnable() {
    @Override
    public void run() {
sliderItems.addAll(sliderItems);
notifyDataSetChanged();
    }
};

    public SlidersAdapter(ViewPager2 viewPager2,List<SliderItems> sliderItems) {
        this.viewPager2 = viewPager2;
        this.sliderItems = sliderItems;
    }



    @NonNull
    @Override
    public SlidersAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context=parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_viewholder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlidersAdapter.SliderViewHolder holder, int position) {
holder.setImage(sliderItems.get(position));
if(position==sliderItems.size()-2){
    viewPager2.post(runnable);
}
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nametxt,genretxt,agetxt,yeartxt,timetxt;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
            nametxt=itemView.findViewById(R.id.nametxt);
            genretxt=itemView.findViewById(R.id.genretext);
            agetxt=itemView.findViewById(R.id.ageTxt);
            yeartxt=itemView.findViewById(R.id.yearTxt);
            timetxt=itemView.findViewById(R.id.timetxt);

        }
        void setImage(SliderItems sliderItems){
            RequestOptions requestOptions=new RequestOptions();
            requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(60));
            Glide.with(context)
                    .load(sliderItems.getImage())
                    .apply(requestOptions)
                    .into(imageView);

            nametxt.setText(sliderItems.getName());
            genretxt.setText(sliderItems.getGenre());
            agetxt.setText(sliderItems.getAge());
            yeartxt.setText(""+sliderItems.getYear());
            timetxt.setText(sliderItems.getTime());

        }
    }

}
