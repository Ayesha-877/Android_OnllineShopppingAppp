package com.example.onlineshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingapp.Domain.CategoryModel;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryModel> items;       // filtered list
    private ArrayList<CategoryModel> fullItems;   // original list
    private Context context;

    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    // ✅ Constructor
    public CategoryAdapter(ArrayList<CategoryModel> items) {
        this.items = items;
        this.fullItems = new ArrayList<>(items); // backup for search
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderCategoryBinding binding =
                ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        CategoryModel model = items.get(position);
        holder.binding.titleTxt.setText(model.getTitle());

        holder.binding.getRoot().setOnClickListener(v -> {
            lastSelectedPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        });

        if (selectedPosition == position) {
            holder.binding.titleTxt.setBackgroundResource(R.drawable.orange_bg);
            holder.binding.titleTxt.setTextColor(
                    context.getResources().getColor(R.color.white));
        } else {
            holder.binding.titleTxt.setBackgroundResource(R.drawable.stroke_bg);
            holder.binding.titleTxt.setTextColor(
                    context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 🔍 SEARCH FILTER METHOD (IMPORTANT)
    public void filter(String text) {
        items.clear();
        selectedPosition = -1; // reset selection on search

        if (text == null || text.trim().isEmpty()) {
            items.addAll(fullItems);
        } else {
            text = text.toLowerCase();
            for (CategoryModel item : fullItems) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;

        public ViewHolder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
