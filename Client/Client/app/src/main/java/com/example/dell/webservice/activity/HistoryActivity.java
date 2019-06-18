package com.example.dell.webservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.example.dell.webservice.adapter.StatusOrderAdapter;
import com.example.dell.webservice.adapter.StoreAdapter;

import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Statusorder;

import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HistoryActivity";
    private RecyclerView rvDelivering;
    private RecyclerView rvDelivered;
    private User user;
    private StatusOrderAdapter deliveringAdapter;
    private StatusOrderAdapter deliveredAdapter;
    private ImageView btnBack;
    private List<Statusorder> notDoneOrder;
    private List<Statusorder> doneOrder;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        receiveData();
        initView();
        HomeActivity.checkNotDoneOrder = false;
        getDoneOrder();
        getNotDoneOrder();
        listennerHander();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (HomeActivity.checkNotDoneOrder) {
                        handler.sendEmptyMessage(100);
                        HomeActivity.checkNotDoneOrder = false;
                    }
                }
            }
        };
        new Thread(runnable).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDoneOrder();
        getNotDoneOrder();
    }

    private void getNotDoneOrder() {
        Client.getService().getNotDoneOrderById(user.getId()).enqueue(new Callback<List<Statusorder>>() {
            @Override
            public void onResponse(Call<List<Statusorder>> call, Response<List<Statusorder>> response) {
                notDoneOrder = response.body();
                deliveringAdapter = new StatusOrderAdapter(HistoryActivity.this, user, notDoneOrder);
                rvDelivering.setAdapter(deliveringAdapter);
                deliveringAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        Statusorder s=notDoneOrder.get(position);
                        Intent intent =new Intent();
                        intent.setClass(HistoryActivity.this,DetailOrderActivity.class);
                        intent.putExtra("USER",user);
                        intent.putExtra("ORDER",s);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Statusorder>> call, Throwable t) {

            }
        });
    }

    private void getDoneOrder() {
        Client.getService().getDoneOrderById(user.getId()).enqueue(new Callback<List<Statusorder>>() {
            @Override
            public void onResponse(Call<List<Statusorder>> call, Response<List<Statusorder>> response) {
                doneOrder = response.body();
                deliveredAdapter = new StatusOrderAdapter(HistoryActivity.this, user, doneOrder);
                rvDelivered.setAdapter(deliveredAdapter);
                deliveredAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        Statusorder s=doneOrder.get(position);
                        Intent intent =new Intent();
                        intent.setClass(HistoryActivity.this,DetailOrderActivity.class);
                        intent.putExtra("USER",user);
                        intent.putExtra("ORDER",s);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Statusorder>> call, Throwable t) {

            }
        });

    }


    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        rvDelivering = findViewById(R.id.rvDelivering);
        rvDelivered = findViewById(R.id.rvDelivered);
    }

    @Override
    public void onClick(View v) {

    }

    private void receiveData() {
        user = (User) getIntent().getSerializableExtra("USER");

    }

    private void listennerHander() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        getDoneOrder();
                        getNotDoneOrder();
                        break;
                }

            }
        };

    }
}
