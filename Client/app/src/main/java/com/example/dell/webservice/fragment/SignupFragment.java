package com.example.dell.webservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.R;

import com.example.dell.webservice.activity.UpdateInfoActivity;
import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.AccountFacebook;
import com.example.dell.webservice.webservice.Client;

import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SighupActivity";
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignup;
    private TextView tvLink;
    private static Account account;
    private AccountFacebook accountFacebook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        receiveData();
        edtUsername = view.findViewById(R.id.input_username);
        edtPhoneNumber = view.findViewById(R.id.input_phone);
        edtEmail = view.findViewById(R.id.input_email);
        edtPassword = view.findViewById(R.id.input_password);
        btnSignup = view.findViewById(R.id.btn_signup);
        tvLink = view.findViewById(R.id.link_login);
        btnSignup.setOnClickListener(this);
        tvLink.setOnClickListener(this);
        if (accountFacebook != null) {
            edtEmail.setText(accountFacebook.getEmail());
        }

    }

    private void receiveData() {
        Bundle bundle = getArguments();
        accountFacebook = (AccountFacebook) bundle.getSerializable("FACEBOOK");
    }

    @Override
    public void onClick(View v) {
        FragmentActivity fragmentActivity = getActivity();
        switch (v.getId()) {
            case R.id.btn_signup:
                if (fragmentActivity instanceof MainActivity) {
                    registerAccount();

                }
                break;
            case R.id.link_login:

                if (fragmentActivity instanceof MainActivity) {

                    ((MainActivity) fragmentActivity).finish();
                    startActivity(new Intent(fragmentActivity, MainActivity.class));
                }
                break;
        }
    }

    private void registerAccount() {
        String userName = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String id = UUID.randomUUID().toString();
        account = new Account(id, userName, password, phone, email);

        Client.getService().registerAccount(account).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                {
                    FragmentActivity fragmentActivity = getActivity();
                    Intent intent = new Intent(getContext(), UpdateInfoActivity.class);
                    intent.putExtra("ACCOUNT", account);
                    intent.putExtra("FACEBOOK",accountFacebook);
                    ((MainActivity) fragmentActivity).finish();
                    Toasty.success(getContext(),"Tạo tài khoản thành công",Toasty.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toasty.error(getContext(), "Tài khoản hoặc email đã tồn tại", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
