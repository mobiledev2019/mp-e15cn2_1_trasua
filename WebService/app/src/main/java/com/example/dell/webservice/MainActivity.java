package com.example.dell.webservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.webservice.Fragment.LoginFragment;
import com.example.dell.webservice.Fragment.SignupFragment;
import com.example.dell.webservice.Fragment.UpdateInfoFragment;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private SignupFragment signupFragment;
//    private HomeFragment homeFragment;
    private UpdateInfoFragment updateInfoFragment;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openLogiScreen();
    }

    public void openLogiScreen(){
        if(loginFragment == null){
            loginFragment = new LoginFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, loginFragment)
                .addToBackStack(null)
                .commit();

    }

    public void openSignupScreen(){
        if(signupFragment == null){
            signupFragment = new SignupFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,signupFragment )
                .addToBackStack(null)
                .commit();

    }



    public void openupdateInfoScreen(){
        if(updateInfoFragment == null){
            updateInfoFragment = new UpdateInfoFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,updateInfoFragment)
                .addToBackStack(null)
                .commit();
    }
}
