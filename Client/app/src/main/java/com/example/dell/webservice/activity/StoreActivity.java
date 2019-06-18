package com.example.dell.webservice.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.webservice.adapter.TeaAdapter;


import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Milktea;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.Tea;
import com.example.dell.webservice.model.Topping;
import com.example.dell.webservice.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class StoreActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "StoreActivity";
    public static boolean check = false;
    private TextView txtStoreName;
    private TextView txtStoreAddress;
    private User user;
    private Store store;
    private TeaAdapter teaAdapter;
    private RecyclerView rvTea;
    private List<Milktea> milkteas;
    private TextView txtCart;
    private Milktea milktea;
    private TextView btnFillter;
    private int choice = 0;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailstore);
        receiveData();
        initView();
        listennerHander();
    }

    private void initView() {
        milkteas = new ArrayList<>();
        txtStoreName = findViewById(R.id.txtStoreName);
        txtStoreAddress = findViewById(R.id.txtStoreAddress);
        if (store != null) {
            txtStoreName.setText(store.getName());
            txtStoreAddress.setText(store.getAddressList().get(0).getDescription());
        }
        rvTea = findViewById(R.id.rvTea);
        teaAdapter = new TeaAdapter(this, user, store.getTeaList());
        rvTea.setAdapter(teaAdapter);
        txtCart = findViewById(R.id.txtCart);
        txtCart.setText(milkteas.size() + " mặt hàng");
        txtCart.setOnClickListener(this);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        teaAdapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Tea tea = store.getTeaList().get(position);
                List<Topping> toppingList = store.getToppingList();
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), OrderActivity.class);
                intent.putExtra("USER", user);
                intent.putExtra("TEA", tea);
                intent.putExtra("TOPPING", (Serializable) toppingList);
                intent.putExtra("STORE", store);
                startActivityForResult(intent, 123);
            }
        });
        btnFillter = findViewById(R.id.btnFillter);
        btnFillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Items
                String[] datas = {"Giá thấp đến cao", "Giá cao đến thấp", "A-Z", "Z-A"};
                AlertDialog.Builder b = new AlertDialog.Builder(StoreActivity.this);
                b.setSingleChoiceItems(datas, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                });
                b.setPositiveButton("Áp dụng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.sendEmptyMessage(101);

                    }
                });
                b.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                b.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (check) {
            switch (requestCode) {
                case 123:
                    milktea = (Milktea) data.getSerializableExtra("milkTea");
                    if (milktea != null) {
                        milkteas.add(milktea);
                    }
                    txtCart.setText(milkteas.size() + " mặt hàng");
                    user = (User) data.getSerializableExtra("USER");
                    store = (Store) data.getSerializableExtra("STORE");
                    check = false;
                    break;
            }
        }


    }

    private void receiveData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");

        store = (Store) intent.getSerializableExtra("STORE");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCart:
                if (milkteas.size() == 0) {
                    Toasty.warning(this, "Vui lòng chọn đồ", Toasty.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(this, ConfirmAcitivity.class);
                intent.putExtra("USER", user);
                intent.putExtra("MILKTEA", (Serializable) milkteas);
                intent.putExtra("STORE", store);
                startActivity(intent);
                break;
        }
    }

    private void listennerHander() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:
                        if (choice == 2) sortNameAZ();
                        if (choice == 3) sortNameZA();
                        if (choice == 0) sortPriceL();
                        if (choice == 1) sortPriceH();
                        break;
                }

            }
        };
    }

    private void sortNameAZ() {
        Comparator<Tea> c = new Comparator<Tea>() {
            @Override
            public int compare(Tea o1, Tea o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Collections.sort(store.getTeaList(), c);
        setListener();

    }

    private void sortNameZA() {
        Comparator<Tea> c = new Comparator<Tea>() {
            @Override
            public int compare(Tea o1, Tea o2) {
                return o2.getName().compareTo(o1.getName());
            }
        };
        Collections.sort(store.getTeaList(), c);
        setListener();
    }

    private void sortPriceL() {
        Comparator<Tea> c = new Comparator<Tea>() {
            @Override
            public int compare(Tea o1, Tea o2) {
                return Integer.parseInt(o1.getPrice()) - Integer.parseInt(o2.getPrice());
            }
        };
        Collections.sort(store.getTeaList(), c);
        setListener();
    }

    private void sortPriceH() {
        Comparator<Tea> c = new Comparator<Tea>() {
            @Override
            public int compare(Tea o1, Tea o2) {
                return Integer.parseInt(o2.getPrice()) - Integer.parseInt(o1.getPrice());
            }
        };
        Collections.sort(store.getTeaList(), c);
        setListener();
    }

    private void setListener() {
        teaAdapter = new TeaAdapter(this, user, store.getTeaList());
        rvTea.setAdapter(teaAdapter);
        teaAdapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Tea tea = store.getTeaList().get(position);
                List<Topping> toppingList = store.getToppingList();
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), OrderActivity.class);
                intent.putExtra("USER", user);
                intent.putExtra("TEA", tea);
                intent.putExtra("TOPPING", (Serializable) toppingList);
                intent.putExtra("STORE", store);
                startActivityForResult(intent, 123);


            }
        });
    }
}
