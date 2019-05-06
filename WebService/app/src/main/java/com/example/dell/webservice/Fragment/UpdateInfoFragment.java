package com.example.dell.webservice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UpdateInfoFragment extends Fragment implements View.OnClickListener {
    String TAG="UpdateInfoFragment";
    private EditText edtFullname;
    private EditText edtDob;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioGroup rgSex;
    private Button btnUpdate;
    private Account account;
    private User user;
//    private HomeFragment homeFragment;
    private static boolean check=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_updateinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edtFullname = view.findViewById(R.id.edtFullName);
        edtDob = view.findViewById(R.id.edtDob);
        rbMale = view.findViewById(R.id.rbMale);
        rbFemale = view.findViewById(R.id.rbFemale);
        rgSex = view.findViewById(R.id.rgSex);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        account = (Account) bundle.getSerializable("ACCOUNT");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                updateInfo();
                break;
        }
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
                    check=true;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                check=true;
            }
        });

                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            Log.d(TAG,String.valueOf(check));
                            if(check==true){
                                if (user != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("USER", user);
//                                    if(homeFragment==null)
//                                    {
//                                        homeFragment=new HomeFragment();
//                                        homeFragment.setArguments(bundle);
//                                    }
//                                    homeFragment.setArguments(bundle);
//                                    getFragmentManager().beginTransaction().replace(android.R.id.content, homeFragment)
//                                            .addToBackStack(null).commit();
//                                    Log.d(TAG,"Fraf");
                                    Intent intent= new Intent(getContext(),HomeActivity.class);
                                    intent.putExtra("USER",user);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    }
                };
                new Thread(runnable).start();

    }
}
