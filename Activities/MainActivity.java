package com.example.movieapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapplication.Adapters.Film_ListAdapter;
import com.example.movieapplication.Adapters.SlidersAdapter;
import com.example.movieapplication.Domains.Film;
import com.example.movieapplication.Domains.SliderItems;
import com.example.movieapplication.Fragments.CartFragment;
import com.example.movieapplication.Fragments.ExplorerFragment;
import com.example.movieapplication.Fragments.FavoritesFragment;
import com.example.movieapplication.Fragments.ProfileFragment;
import com.example.movieapplication.R;
import com.example.movieapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
private static final String TAG=MainActivity.class.getSimpleName();
TextView n,emu;
    FragmentManager fragmentManager;

ChipNavigationBar navbar;



private Handler sliderHandler=new Handler();
  private Runnable sliderRunnable= new Runnable() {
      @Override
      public void run() {
          binding.viewPager2.setCurrentItem(binding.viewPager2.getCurrentItem()+1);
      }
  };
private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

n=findViewById(R.id.username);
emu=findViewById(R.id.emailofuser);
navbar=findViewById(R.id.navbar);

binding.navbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(int id) {
     Fragment fragment=null;
        switch (id) {
            case R.id.Profile:
                fragment = new ProfileFragment();
                break;
            case R.id.explorer:
                fragment = new ExplorerFragment();
                break;
            case R.id.Favourites:
                fragment = new FavoritesFragment();
                break;
            case R.id.Cart:
                fragment = new CartFragment();
                break;

        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
        }
        else {
            Log.d(TAG, "Error in creating fragment");
        }
    }

});

        database=FirebaseDatabase.getInstance();
        Intent i=getIntent();
        String e=i.getStringExtra("email").toString();
        String ni=i.getStringExtra("name").toString();
        openFragment(ni,e);
        emu.setText(e);
        n.setText("Hello!! " +ni);
        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initBanner();
initTopMoving();
        initUpcoming();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initUpcoming(){
        DatabaseReference myRef=database.getReference("Upcomming");
        binding.progressBar3.setVisibility(View.VISIBLE);
        ArrayList<Film>items=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
                    if(!items.isEmpty()){
                        binding.recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        binding.recyclerViewUpcoming.setAdapter(new Film_ListAdapter(items));
                    }
                    binding.progressBar3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initTopMoving(){
        DatabaseReference myRef=database.getReference("Items");
        binding.progressBar2.setVisibility(View.VISIBLE);
        ArrayList<Film>items=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
if(!items.isEmpty()){
    binding.recyclerViewTopMovies.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
    binding.recyclerViewTopMovies.setAdapter(new Film_ListAdapter(items));
}
                    binding.progressBar2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initBanner(){
        DatabaseReference myRef=database.getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems>items=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
private void banners(ArrayList<SliderItems>items){
        binding.viewPager2.setAdapter(new SlidersAdapter(binding.viewPager2, items));
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

    CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
    compositePageTransformer.addTransformer(new MarginPageTransformer(40));
    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
        @Override
        public void transformPage(@NonNull View page, float position) {
            float r=1-Math.abs(position);
            page.setScaleY(0.85f+r*0.15f);
        }
    });

    binding.viewPager2.setPageTransformer(compositePageTransformer);
    binding.viewPager2.setCurrentItem(1);
    binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
          sliderHandler.removeCallbacks(sliderRunnable);
        }
    });
}

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,2000);
    }
    private void openFragment(String name,String email){

        Bundle bundle=new Bundle();
        bundle.putString("name",name.toString());
        bundle.putString("email",email.toString());
         Fragment fragment=new Fragment();
        fragment.setArguments(bundle);
    getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,fragment)
              .commit();

    }
}