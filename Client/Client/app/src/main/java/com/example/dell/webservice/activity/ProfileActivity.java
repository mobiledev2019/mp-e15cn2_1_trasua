package com.example.dell.webservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.webservice.R;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    private ImageView btnEdit;
    private EditText edtEmail;
    private EditText editPhone;
    private EditText editDob;
    private EditText editName;
    private TextView txtPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        receiveData();
        initView();
        getNewData();

    }

    private void initView() {
        txtPoint=findViewById(R.id.txtPoint);
        editName=findViewById(R.id.edtName);
        editDob=findViewById(R.id.edtDob);
        editPhone=findViewById(R.id.edtPhone);
        edtEmail=findViewById(R.id.edtEmail);
        ImageView btnBack= findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        Button btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        Button btnUpdate=findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName=editName.getText().toString();
                String dob=editDob.getText().toString();
                String phone=editPhone.getText().toString();
                String email=edtEmail.getText().toString();
                user.setFullname(fullName);
                user.setDob(dob);
                user.getAccountId().setPhone(phone);
                user.getAccountId().setEmail(email);
                Client.getService().updateUserInfo(user).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toasty.success(ProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ProfileActivity.this,HomeActivity.class);
                        intent.putExtra("USER",user);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getNewData() {
         Client.getService().checkLogin(user.getAccountId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user=response.body();
                updateInfo();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void receiveData() {
        user= (User) getIntent().getSerializableExtra("USER");
    }
    private void updateInfo(){
        txtPoint.setText(String.valueOf(user.getPoint()));
        editName.setText(user.getFullname());
        editDob.setText(user.getDob());
        editPhone.setText(user.getAccountId().getPhone());
        edtEmail.setText(user.getAccountId().getEmail());

    }
}
