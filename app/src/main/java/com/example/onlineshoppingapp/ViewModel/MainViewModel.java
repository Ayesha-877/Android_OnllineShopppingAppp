package com.example.onlineshoppingapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshoppingapp.Domain.BannerModel;
import com.example.onlineshoppingapp.Domain.CategoryModel;
import com.example.onlineshoppingapp.Domain.ItemsModel;
import com.example.onlineshoppingapp.Repository.MainRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
private final MainRepository repository=new MainRepository();

public LiveData<ArrayList<CategoryModel>> loadCategory(){
    return repository.loadCategory();
}

public LiveData<ArrayList<BannerModel>> loadBanner(){
    return repository.loadBanner();
}

public LiveData<ArrayList<ItemsModel>> loadPopular(){
    return repository.loadpopular();

}
}
