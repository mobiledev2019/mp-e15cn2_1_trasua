package com.example.dell.webservice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.webservice.adapter.MilkTeaAdapter;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Milktea;
import com.example.dell.webservice.model.Statusorder;
import com.example.dell.webservice.model.User;

import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {
    TextView txtStore;
    TextView txtAddress;
    Statusorder statusorder;
    MilkTeaAdapter milkTeaAdapter;
    RecyclerView rvMilkTea;
    TextView txtStatus;
    User user;
    TextView txtPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        receiveData();
        initView();
    }

    private void receiveData() {
        Intent intent = getIntent();
        statusorder = (Statusorder) intent.getSerializableExtra("ORDER");
        user = (User) intent.getSerializableExtra("USER");

    }

    private void initView() {
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        txtStore = findViewById(R.id.txtStore);
        txtAddress = findViewById(R.id.txtAddress);
        txtStatus = findViewById(R.id.txtStatus);
        rvMilkTea = findViewById(R.id.rvMilkTea);
        milkTeaAdapter = new MilkTeaAdapter(this, user, statusorder.getOrderId().getMilkteaList());
        milkTeaAdapter.setOnItemClickListener(new MilkTeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
        rvMilkTea.setAdapter(milkTeaAdapter);
        if (statusorder.getStatus().equalsIgnoreCase("Đã giao"))
            txtStatus.setTextColor(Color.RED);
        else
            txtStatus.setTextColor(0XFF348118);
        if (statusorder.getOrderId().getAddressShipId() != null) {
            txtAddress.setText(statusorder.getOrderId().getAddressShipId().toString());
        }
        txtStore.setText(statusorder.getOrderId().getStore().getName() + " - " + statusorder.getOrderId().getStore().getAddressList().toString());
        txtPrice = findViewById(R.id.txtPrice);
        List<Milktea> milkteaList = statusorder.getOrderId().getMilkteaList();
        int price = 0;
        for (Milktea m : milkteaList) {
            String priceTopping = m.getTopping().getPrice();
            String price1 = m.getTeaId().getPrice();
            if (m.getSize().equalsIgnoreCase("M")) {
                price = price + Integer.parseInt(price1) + Integer.parseInt(priceTopping);
            } else
                price = price + Integer.parseInt(price1) + 7000 + Integer.parseInt(priceTopping);
            ;

        }
        txtPrice.setText(milkteaList.size() + " phần - " + String.valueOf(price) + " VNĐ");
        txtStatus.setText(statusorder.getStatus());
    }
}
