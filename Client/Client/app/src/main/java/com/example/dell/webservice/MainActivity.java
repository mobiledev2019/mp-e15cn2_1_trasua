package com.example.dell.webservice;

import android.Manifest;
import android.annotation.TargetApi;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dell.webservice.activity.HomeActivity;

import com.example.dell.webservice.fragment.SignupFragment;
import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.AccountFacebook;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private SignupFragment signupFragment;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvLinkSignup;
    private User user = null;
    AccountFacebook accountFacebook;
    private FirebaseAuth mAuth;


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            return;
        }

        if (checkSelfPermission(Manifest.permission_group.LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            return;
        }

        String[] permissions = new String[2];
        permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        checkPermissions();
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.btnLoginByFacebook);
        loginButton.setLoginText("Đăng ký với Facebook");
        loginButton.setLogoutText("Đăng xuất");
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_gender", "user_birthday"));
        setLogin_Button();
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLinkSignup = findViewById(R.id.link_signup);
        btnLogin.setOnClickListener(this);
        tvLinkSignup.setOnClickListener(this);
        LoginManager.getInstance().logOut();


    }


    public void openSignupScreen() {
        if (signupFragment == null) {
            signupFragment = new SignupFragment();
        }

        accountFacebook = new AccountFacebook("", "", "", "");
        Bundle bundle = new Bundle();
        bundle.putSerializable("FACEBOOK", accountFacebook);
        signupFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, signupFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this,
                        "Vui lòng cấp quyền để sử dụng",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = response.getJSONObject().getString("name");
                    String dob = response.getJSONObject().getString("birthday");
                    String email = response.getJSONObject().getString("email");
                    String gender = response.getJSONObject().getString("gender");
                    accountFacebook = new AccountFacebook(name, email, gender, dob);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (signupFragment == null) {
                    signupFragment = new SignupFragment();
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("FACEBOOK", accountFacebook);
                signupFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, signupFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    private void checkLogin(String username, String password) {
        Client.getService().checkLogin(new Account(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user == null) {
                    Toasty.error(MainActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();

                }
                else
                {

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("USER", user);
                        startActivity(intent);
                        finish();


                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.error(MainActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin: {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                checkLogin(username, password);
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//
//                            if (user != null) {
//                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                intent.putExtra("USER", user);
//                                startActivity(intent);
//                                finish();
//                                break;
//                            }
//
//                        }
//                    }
//                };
//                new Thread(runnable).start();
            }
            break;

            case R.id.link_signup:
                openSignupScreen();
                break;


        }

    }
}
