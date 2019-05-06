package com.example.dell.webservice.Fragment;

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

import com.example.dell.webservice.HomeActivity;
import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "LoginFragment";
//   private HomeFragment homeFragment;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvLinkSignup;
    private User user = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvLinkSignup = view.findViewById(R.id.link_signup);
        btnLogin.setOnClickListener(this);
        tvLinkSignup.setOnClickListener(this);


    }

    private void checkLogin(String username, String password) {
        Client.getService().checkLogin(new Account(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, response.body().toString());
                user = response.body();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        FragmentActivity fragmentActivity = getActivity();
        switch (v.getId()) {
            case R.id.btnLogin:
                if (fragmentActivity instanceof MainActivity) {
                    String username = edtUsername.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    checkLogin(username, password);
                    Runnable runnable =new Runnable() {
                        @Override
                        public void run() {
                            while (true){

                                if (user != null&&username.equals(user.getAccountId().getUsername())) {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("USER", user);
//                                    if(homeFragment==null)
//                                    {
//                                        homeFragment=new HomeFragment();
//                                        homeFragment.setArguments(bundle);
//                                    }
//                                    homeFragment.setArguments(bundle);
//                                    getFragmentManager().beginTransaction().replace(android.R.id.content, homeFragment)
//                                            .addToBackStack(null).commit();
                                    Intent intent= new Intent(getContext(),HomeActivity.class);
                                    intent.putExtra("USER",user);
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                    };
                    new Thread(runnable).start();
                }
                break;

            case R.id.link_signup:
                ((MainActivity) fragmentActivity).openSignupScreen();
                break;

        }

    }
}
