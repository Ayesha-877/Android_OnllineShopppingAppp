package com.example.onlineshoppingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.databinding.ViewholderColorBinding;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.Viewholder> {

    private final ArrayList<String> items;
    private Context context;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public ColorAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderColorBinding binding =
                ViewholderColorBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.binding.getRoot().setOnClickListener(v -> {

            int clickedPosition = holder.getBindingAdapterPosition();
            if (clickedPosition == RecyclerView.NO_POSITION) return;

            lastSelectedPosition = selectedPosition;
            selectedPosition = clickedPosition;

            if (lastSelectedPosition != -1) {
                notifyItemChanged(lastSelectedPosition);
            }
            notifyItemChanged(selectedPosition);
        });

        Drawable baseDrawable =
                AppCompatResources.getDrawable(context, R.drawable.color_selected);

        if (baseDrawable == null) return;

        Drawable drawable = DrawableCompat.wrap(baseDrawable.mutate());

        if (selectedPosition == position) {
            // Selected state (no tint)
            Glide.with(context)
                    .load(drawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.colorLayoout);
        } else {
            // Normal state (apply color)
            DrawableCompat.setTint(drawable, Color.parseColor(items.get(position)));

            Glide.with(context)
                    .load(drawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.colorLayoout);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderColorBinding binding;

        public Viewholder(ViewholderColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
