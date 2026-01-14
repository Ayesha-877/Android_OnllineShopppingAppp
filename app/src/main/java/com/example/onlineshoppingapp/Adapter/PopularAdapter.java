package com.example.onlineshoppingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.example.onlineshoppingapp.Activity.DetailActivity;
import com.example.onlineshoppingapp.Domain.ItemsModel;
import com.example.onlineshoppingapp.databinding.ViewholderPopularBinding;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {

    private ArrayList<ItemsModel> itemsModels;      // filtered list
    private ArrayList<ItemsModel> fullItems;        // original list
    private Context context;

    // ✅ Constructor
    public PopularAdapter(ArrayList<ItemsModel> itemsModels) {
        this.itemsModels = itemsModels;
        this.fullItems = new ArrayList<>(itemsModels); // backup list
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderPopularBinding binding =
                ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {

        ItemsModel item = itemsModels.get(position);

        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$" + item.getPrice());
        holder.binding.ratingTxt.setText("(" + item.getRating() + ")");
        holder.binding.offpercenttxt.setText(item.getOffPercent() + " off");
        holder.binding.oldPriceTxt.setText("$" + item.getOldPrice());
        holder.binding.oldPriceTxt.setPaintFlags(
                holder.binding.oldPriceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );

        RequestOptions options = new RequestOptions().transform(new CenterInside());

        Glide.with(context)
                .load(item.getPicUrl().get(0))
                .apply(options)
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemsModels.size();
    }

    // 🔍 SEARCH FILTER METHOD
    public void filter(String text) {
        itemsModels.clear();

        if (text == null || text.trim().isEmpty()) {
            itemsModels.addAll(fullItems);
        } else {
            text = text.toLowerCase();
            for (ItemsModel item : fullItems) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    itemsModels.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderPopularBinding binding;

        public Viewholder(ViewholderPopularBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
