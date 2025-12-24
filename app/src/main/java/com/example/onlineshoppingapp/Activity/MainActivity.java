package com.example.onlineshoppingapp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.onlineshoppingapp.Adapter.CategoryAdapter;
import com.example.onlineshoppingapp.Adapter.Slider_Adapter;
import com.example.onlineshoppingapp.Domain.BannerModel;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.ViewModel.MainViewModel;
import com.example.onlineshoppingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
     private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel=new MainViewModel();

        initCategory();
        initSlider();
    }

    private void initSlider() {
        binding.bannerProgressSlider.setVisibility(View.VISIBLE);
        viewModel.loadBanner().observeForever(bannerModels -> {
            if(bannerModels!=null && !bannerModels.isEmpty()){
                banners(bannerModels);
                binding.bannerProgressSlider.setVisibility(View.GONE);
            }
        });
        viewModel.loadBanner();

    }

    private void banners(ArrayList<BannerModel> bannerModels) {
        binding.bannerViewPager.setAdapter(new Slider_Adapter(bannerModels,binding.bannerViewPager));
        binding.bannerViewPager.setClipToPadding(false);
        binding.bannerViewPager.setClipChildren(false);
        binding.bannerViewPager.setOffscreenPageLimit(3);
        binding.bannerViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.bannerViewPager.setPageTransformer(compositePageTransformer);
    }

    private void initCategory() {
        binding.ProgressBarCategory.setVisibility(View.VISIBLE);
        viewModel.loadCategory().observeForever(categoryModels -> {
            binding.categoryView.setLayoutManager(new LinearLayoutManager(
                    MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
            binding.categoryView.setAdapter(new CategoryAdapter(categoryModels));
            binding.categoryView.setNestedScrollingEnabled(true);
            binding.ProgressBarCategory.setVisibility(View.GONE);
        });
    }
}