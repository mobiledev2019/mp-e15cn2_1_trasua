package com.example.dell.webservice.activity;

import android.app.Activity;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dell.webservice.adapter.ToppingAdapter;

import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Milktea;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.Tea;
import com.example.dell.webservice.model.Topping;
import com.example.dell.webservice.model.User;

import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "OrderActivity";
    private RadioButton rbSizeM;
    private RadioButton rbSizeL;
    private RecyclerView rvTopping;
    private ToppingAdapter toppingAdapter;
    private Button btnCancel;
    private Button btnAddToCart;
    private Tea tea;
    private List<Topping> toppingList;
    private User user;
    private TextView txtTea;
    private TextView txtPriceM;
    private TextView txtPriceL;
    private Milktea milktea;
    private Topping topping;
    private TextView txtSelected;
    private Store store;

    @Override
    public void onStart() {
        super.onStart();
        reciveData();
        setContentView(R.layout.activity_order);
        ImageView btnBack= findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        rbSizeM = findViewById(R.id.rbSizeM);
        rbSizeL = findViewById(R.id.rbSizeL);
        btnCancel = findViewById(R.id.btnCancel);
        txtTea = findViewById(R.id.txtTea);
        txtPriceM = findViewById(R.id.txtPriceM);
        txtPriceL = findViewById(R.id.txtPriceL);
        txtSelected = findViewById(R.id.txtSelected);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        TextView txtStore=findViewById(R.id.txtStore);
        txtStore.setText(store.getName());
        txtTea.setText(tea.getName());
        txtPriceM.setText("Size M: "+tea.getPrice());
        txtPriceL.setText("Size L: "+String.valueOf(Integer.parseInt(tea.getPrice()) + 7000));
        rvTopping = findViewById(R.id.rvTopping);
        toppingAdapter = new ToppingAdapter(this, user, toppingList);
        rvTopping.setAdapter(toppingAdapter);
        toppingAdapter.setOnItemClickListener(new ToppingAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                topping = toppingList.get(position);

                if(rbSizeL.isChecked())
                    txtSelected.setText("Đã chọn: "+topping.getName());
                else
                    txtSelected.setText("Đã chọn: "+topping.getName());
            }
        });

    }


    void reciveData() {
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("STORE");
        user = (User) intent.getSerializableExtra("USER");
        tea = (Tea) intent.getSerializableExtra("TEA");
        toppingList = (List<Topping>) intent.getSerializableExtra("TOPPING");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart:
                if(txtSelected.getText().toString().equalsIgnoreCase("")){
                    Toasty.warning(this,"Chọn Topping",Toasty.LENGTH_SHORT).show();
                    return;
                }
                milktea = new Milktea();
                String size = "";
                if (rbSizeM.isChecked())
                    size = "M";
                if (rbSizeL.isChecked())
                    size = "L";

                milktea.setId(UUID.randomUUID().toString());
                milktea.setSize(size);
                milktea.setTopping(topping);
                milktea.setTeaId(tea);
                Intent intent = new Intent();
                intent.setClass(this, StoreActivity.class);
                intent.putExtra("milkTea", milktea);
                intent.putExtra("STORE", store);
                intent.putExtra("USER",user);
                StoreActivity.check=true;
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            case R.id.btnCancel:
                finish();
            case R.id.btnBack:
                finish();
                onBackPressed();
                break;
        }
    }
}
