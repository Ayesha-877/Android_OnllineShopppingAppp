package com.example.onlineshoppingapp.Activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.onlineshoppingapp.Adapter.ColorAdapter;
import com.example.onlineshoppingapp.Adapter.PicListAdapter;
import com.example.onlineshoppingapp.Adapter.sizeAdapter;
import com.example.onlineshoppingapp.Domain.ItemsModel;
import com.example.onlineshoppingapp.Helper.ManagmentCart;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ItemsModel object;
    private int numberOrder=1;
    private ManagmentCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart=new ManagmentCart(this);

        getBundles();
        initPicList();
        initSize();
        initColors();

    }

    private void initColors() {
        binding.recyclerColor.setAdapter(new ColorAdapter(object.getColor()));
        binding.recyclerColor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initSize() {
        binding.recyclerSize.setAdapter(new sizeAdapter(object.getSize()));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initPicList() {
        ArrayList<String> picList=new ArrayList<>(object.getPicUrl());
        Glide.with(this)
                .load(picList.get(0))
                .into(binding.pic);

        binding.picList.setAdapter(new PicListAdapter(picList,binding.pic));
        binding.picList.
                setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void getBundles() {
        object= (ItemsModel) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.oldPriceTxt.setText("$"+object.getOldPrice());
        binding.oldPriceTxt.setPaintFlags(binding.oldPriceTxt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        binding.descriptionTxt.setText(object.getDescription());

        binding.addToCart.setOnClickListener(view -> {
            object.setNumberinCart(numberOrder);
            managementCart.insertItem(object);
        });

        binding.BackBtn.setOnClickListener(view -> finish());
    }
}