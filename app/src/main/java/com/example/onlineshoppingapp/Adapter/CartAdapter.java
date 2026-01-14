package com.example.onlineshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshoppingapp.Domain.ItemsModel;
import com.example.onlineshoppingapp.Helper.ChangeNumberItemsListener;
import com.example.onlineshoppingapp.Helper.ManagmentCart;
import com.example.onlineshoppingapp.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<ItemsModel> listItemSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<ItemsModel> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart=new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding=ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        holder.binding.titleTxt.setText(listItemSelected.get(position).getTitle());
        holder.binding.feeEachItem.setText("$"+listItemSelected.get(position).getPrice());
        holder.binding.totalfee.setText("$"+Math.round((listItemSelected.get(position).getNumberinCart()*
                listItemSelected.get(position).getPrice())));
        holder.binding.numberItemtxt.setText(String.valueOf(listItemSelected.get(position).getNumberinCart()));

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getPicUrl().get(0))
                .into(holder.binding.pic);

        holder.binding.AddCarBtn.setOnClickListener(view -> managmentCart.plusItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.binding.minusCartBtn.setOnClickListener(view -> managmentCart.minusItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;
        public Viewholder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
