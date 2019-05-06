package com.example.dell.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dell.webservice.Adapter.StoreAdapter;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    String TAG = "HomeActivity";
    private User user;
    private RecyclerView rvStore;
    private static List<Store> storeList;
    private StoreAdapter storeAdapter;
    private StoreAdapter listFavoriteAdapter;
    private RecyclerView rvListFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        receiveData();
        initView();
        Client.getService().getAllStore().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                storeList = response.body();
                if(storeList!=null){
                    storeAdapter = new StoreAdapter(HomeActivity.this, user, storeList);
                    rvStore.setAdapter(storeAdapter);
                    storeAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(int position) {
                            Intent intent =new Intent(HomeActivity.this,StoreActivity.class);
                            intent.putExtra("USER",user);
                            intent.putExtra("STORE",storeList.get(position));
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {


            }
        });


    }

    private void initView() {
        rvStore=findViewById(R.id.rvStore);
        rvListFavorite=findViewById(R.id.rvListfavorite);
        if(user.getListFavoriteId()!=null){
            listFavoriteAdapter = new StoreAdapter(this,user,user.getListFavoriteId().getStoreList());
            rvListFavorite.setAdapter(listFavoriteAdapter);
            listFavoriteAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(int position) {
                    Intent intent =new Intent(HomeActivity.this,StoreActivity.class);
                    intent.putExtra("USER",user);
                    intent.putExtra("STORE",user.getListFavoriteId().getStoreList().get(position));
                    startActivity(intent);
                }
            });
        }
    }


    private void receiveData() {
        user = (User) getIntent().getSerializableExtra("USER");

    }

}