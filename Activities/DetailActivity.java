package com.example.movieapplication.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.Adapters.CastListAdapter;
import com.example.movieapplication.Adapters.CategoryEachFilmAdapter;
import com.example.movieapplication.Domains.Film;
import com.example.movieapplication.R;
import com.example.movieapplication.databinding.ActivityDetailBinding;
import com.example.movieapplication.databinding.ActivityMainBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class DetailActivity extends AppCompatActivity {
private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
    private void setVariable(){
        Film item=(Film) getIntent().getSerializableExtra("object");
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(),new GranularRoundedCorners(0,0,50,50));

    Glide.with(this)
            .load(item.getPoster())
            .apply(requestOptions)
            .into(binding.filmPic);

    binding.titletxt.setText(item.getTitle());
    binding.imdbtxt.setText("IMDB"+item.getImdb());
    binding.movieTimestxt.setText(item.getYear()+" - "+item.getTime());
    binding.movieSummarytxt.setText(item.getDescription());

    binding.watchTrailerbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id=item.getTrailer().replace("https://www.youtube.com/watch?v=","");
            Intent appIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+id));
            Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(item.getTrailer()));

            try{
                startActivity(appIntent);
            }catch (ActivityNotFoundException ex){
                startActivity(webIntent);
            }
        }
    });
binding.backimg.setOnClickListener(v-> finish());

float radius=10f;
View decorView=getWindow().getDecorView();
        ViewGroup rootView=(ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground=decorView.getBackground();

        binding.blurView.setupWith(rootView,new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);
        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

        if(item.getGenre()!=null){
            binding.genreView.setAdapter(new CategoryEachFilmAdapter(item.getGenre()));
            binding.genreView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        }
        if(item.getCasts()!=null){
            binding.CastView.setAdapter(new CastListAdapter(item.getCasts()));
            binding.CastView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}