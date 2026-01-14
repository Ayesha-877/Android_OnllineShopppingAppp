package com.example.onlineshoppingapp.Activity;

import android.content.Intent;
import android.os.Bundle;import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.onlineshoppingapp.Adapter.CategoryAdapter;
import com.example.onlineshoppingapp.Adapter.PopularAdapter;
import com.example.onlineshoppingapp.Adapter.Slider_Adapter;
import com.example.onlineshoppingapp.Domain.BannerModel;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.ViewModel.MainViewModel;
import com.example.onlineshoppingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private CategoryAdapter categoryAdapter;

    private static final int VOICE_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initCategory();
        initSlider();
        initPopular();
        bottomNavigation();
        setupSearch();
        setupVoiceSearch();
        initChatButton(); // Added this
    }

    // ================= AI CHAT =================
    private void initChatButton() {
        binding.chatBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });
    }

    // ================= SEARCH =================
    private void setupSearch() {
        binding.editTextText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (categoryAdapter != null) {
                    categoryAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // ================= VOICE SEARCH =================
    private void setupVoiceSearch() {
        binding.editTextText2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >=
                        (binding.editTextText2.getRight()
                                - binding.editTextText2.getCompoundDrawables()[2].getBounds().width())) {
                    startVoiceSearch();
                    return true;
                }
            }
            return false;
        });
    }

    private void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search category");

        try {
            startActivityForResult(intent, VOICE_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Voice search not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String voiceText = result.get(0);
                binding.editTextText2.setText(voiceText);
                if (categoryAdapter != null) {
                    categoryAdapter.filter(voiceText);
                }
            }
        }
    }

    // ================= BOTTOM NAVIGATION =================
    private void bottomNavigation() {
        binding.bottomNavigation.setItemSelected(R.id.home, true);

        binding.bottomNavigation.setOnItemSelectedListener(i -> {
            // future navigation
        });

        binding.CartBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CartActivity.class))
        );
    }


    // ================= CATEGORY =================
    private void initCategory() {
        binding.ProgressBarCategory.setVisibility(View.VISIBLE);

        binding.categoryView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        viewModel.loadCategory().observe(this, categoryModels -> {
            categoryAdapter = new CategoryAdapter(categoryModels);
            binding.categoryView.setAdapter(categoryAdapter);
            binding.ProgressBarCategory.setVisibility(View.GONE);
        });
    }

    // ================= SLIDER =================
    private void initSlider() {
        binding.bannerProgressSlider.setVisibility(View.VISIBLE);

        viewModel.loadBanner().observe(this, bannerModels -> {
            if (bannerModels != null && !bannerModels.isEmpty()) {
                banners(bannerModels);
            }
            binding.bannerProgressSlider.setVisibility(View.GONE);
        });
    }

    private void banners(ArrayList<BannerModel> bannerModels) {
        binding.bannerViewPager.setAdapter(
                new Slider_Adapter(bannerModels, binding.bannerViewPager)
        );

        binding.bannerViewPager.setClipToPadding(false);
        binding.bannerViewPager.setClipChildren(false);
        binding.bannerViewPager.setOffscreenPageLimit(3);
        binding.bannerViewPager.getChildAt(0)
                .setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        binding.bannerViewPager.setPageTransformer(transformer);
    }

    // ================= POPULAR =================
    private void initPopular() {
        binding.ProgressBarPopular.setVisibility(View.VISIBLE);

        binding.popularView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        viewModel.loadPopular().observe(this, itemsModels -> {
            binding.popularView.setAdapter(new PopularAdapter(itemsModels));
            binding.ProgressBarPopular.setVisibility(View.GONE);
        });
    }
}
