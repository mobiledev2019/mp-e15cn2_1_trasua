package com.example.dell.webservice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.R;

import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.webservice.Client;

import java.util.UUID;

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
    private UpdateInfoFragment updateInfoFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edtUsername = view.findViewById(R.id.input_username);
        edtPhoneNumber = view.findViewById(R.id.input_phone);
        edtEmail = view.findViewById(R.id.input_email);
        edtPassword = view.findViewById(R.id.input_password);
        btnSignup = view.findViewById(R.id.btn_signup);
        tvLink = view.findViewById(R.id.link_login);

        btnSignup.setOnClickListener(this);

        tvLink.setOnClickListener(this);


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

                    ((MainActivity) fragmentActivity).openLogiScreen();
                }
                break;
        }
    }

    private void registerAccount() {
        String userName= edtUsername.getText().toString();
        String password= edtPassword.getText().toString();
        String phone=edtPhoneNumber.getText().toString();
        String email=edtEmail.getText().toString();
        String id= UUID.randomUUID().toString();
        account=new Account(id,userName,password,phone,email);

        Client.getService().registerAccount(account).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               if (updateInfoFragment==null)
               {
                   updateInfoFragment=new UpdateInfoFragment();
                   Bundle bundle=new Bundle();
                   bundle.putSerializable("ACCOUNT",account);
                   updateInfoFragment.setArguments(bundle);
                   getFragmentManager().beginTransaction().replace(android.R.id.content,updateInfoFragment)
                           .addToBackStack(null).commit();
               }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
