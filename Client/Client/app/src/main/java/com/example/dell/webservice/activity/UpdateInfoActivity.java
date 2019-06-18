package com.example.dell.webservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.AccountFacebook;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtFullname;
    private EditText edtDob;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioGroup rgSex;
    private Button btnUpdate;
    private Account account;
    private User user;
    private User getUser;
    AccountFacebook accountFacebook;
    //    private HomeFragment homeFragment;
    private static boolean check = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);
        edtFullname = findViewById(R.id.edtFullName);
        edtDob = findViewById(R.id.edtDob);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rgSex = findViewById(R.id.rgSex);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        receiveData();
        if (accountFacebook != null) {
            edtFullname.setText(accountFacebook.getName());
            edtDob.setText(accountFacebook.getDob());
            if (accountFacebook.getGender().equalsIgnoreCase("male"))
                rbMale.setSelected(true);
            else
                rbFemale.setSelected(true);
        }
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    private void receiveData() {
        Intent intent = getIntent();
        accountFacebook = (AccountFacebook) intent.getSerializableExtra("FACEBOOK");
        account = (Account) intent.getSerializableExtra("ACCOUNT");
    }

    private void updateInfo() {
        String fullName = edtFullname.getText().toString();
        String dob = edtDob.getText().toString();
        String sex = null;
        if (rbFemale.isChecked()) {
            sex = "Nữ";
        }

        if (rbMale.isChecked()) {
            sex = "Nam";
        }
        user = new User(fullName, sex, dob, account);
        user.setPoint(0);
        Client.getService().updateInfo(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                check = true;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                check = true;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkLogin(user.getAccountId().getUsername(), user.getAccountId().getPassword());
                while (true) {
                    if (check == true) {
                        if (getUser != null) {
                            Intent intent = new Intent(UpdateInfoActivity.this, HomeActivity.class);
                            intent.putExtra("USER", getUser);
                            startActivity(intent);
                            finish();
                            break;

                        }
                    }
                }
            }
        };
        new Thread(runnable).start();

    }

    private void checkLogin(String username, String password) {
        Client.getService().checkLogin(new Account(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getUser = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                updateInfo();
                break;
            case R.id.btnBack:
                finish();
                startActivity(new Intent(UpdateInfoActivity.this,MainActivity.class));
                break;

        }
    }
}
