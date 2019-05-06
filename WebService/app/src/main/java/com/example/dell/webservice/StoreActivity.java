package com.example.dell.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.dell.webservice.Adapter.TeaAdapter;
import com.example.dell.webservice.Adapter.ToppingAdapter;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;

public class StoreActivity extends AppCompatActivity {
    private TextView txtStoreName;
    private TextView txtStoreAddress;
    private User user;
    private Store store;
    private TeaAdapter teaAdapter;
    private ToppingAdapter toppingAdapter;
    private RecyclerView rvTea;
    private RecyclerView rvTopping;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailstore);
        receiveData();
        initView();
    }

    private void initView() {
        txtStoreName=findViewById(R.id.txtStoreName);
        txtStoreAddress=findViewById(R.id.txtStoreAddress);
        txtStoreName.setText(store.getName());
        txtStoreAddress.setText(store.getAddressList().get(0).getDescription());
        rvTea=findViewById(R.id.rvTea);
        teaAdapter=new TeaAdapter(this,user,store.getTeaList());
        rvTea.setAdapter(teaAdapter);
        rvTopping=findViewById(R.id.rvTopping);
        toppingAdapter=new ToppingAdapter(this,user,store.getToppingList());
        rvTopping.setAdapter(toppingAdapter);
    }

    private void receiveData() {
        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("ƯSER");
        store= (Store) intent.getSerializableExtra("STORE");
    }
}
