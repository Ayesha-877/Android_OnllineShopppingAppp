package com.example.onlineshoppingapp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshoppingapp.Adapter.CartAdapter;
import com.example.onlineshoppingapp.Helper.ChangeNumberItemsListener;
import com.example.onlineshoppingapp.Helper.ManagmentCart;
import com.example.onlineshoppingapp.R;
import com.example.onlineshoppingapp.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managementCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart=new ManagmentCart(this);
        calculatorCart();
        setVeriable();
        initCartList();

    }

    private void initCartList() {
        if (managementCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollView2.setVisibility(View.GONE);

        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollView2.setVisibility(View.VISIBLE);
        }

        binding.CartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.CartView.setAdapter(new CartAdapter(managementCart.getListCart(), this, this::calculatorCart));
    }

    private void setVeriable() {
        binding.BackBtn.setOnClickListener(view -> finish());
    }

    private void calculatorCart() {
        double percentTax=0.02;
        double delivery=10;
        tax=Math.round((managementCart.getTotalFee()*percentTax*100.0))/100.0;
        double total=Math.round((managementCart.getTotalFee()+tax+delivery))*100.0/100.0;
        double itemTotal=Math.round((managementCart.getTotalFee()*100.0))/100.0;

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.texTxt.setText("$"+delivery);
        binding.deliverTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);


    }
}