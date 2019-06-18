package com.example.dell.webservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.adapter.StoreAdapter;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Registrator;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean checkNotDoneOrder = false;
    String TAG = "HomeActivity";
    private User user;
    private RecyclerView rvStore;
    private static List<Store> storeList;
    private StoreAdapter storeAdapter;
    private StoreAdapter listFavoriteAdapter;
    private RecyclerView rvListFavorite;
    private TextView btnSearch;
    private EditText edtKey;
    private TextView txtList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        receiveData();
        initView();
        Client.getService().getAllStore().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                storeList = response.body();
                if (storeList != null) {
                    storeAdapter = new StoreAdapter(HomeActivity.this, user, storeList);
                    rvStore.setAdapter(storeAdapter);
                    storeAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(int position) {
                            Intent intent = new Intent(HomeActivity.this, StoreActivity.class);
                            intent.putExtra("USER", user);
                            Log.d(TAG, user.toString());
                            intent.putExtra("STORE", storeList.get(position));
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(HomeActivity.this, MapActivity.class);
            intent.putExtra("USER", user);
            intent.putExtra("STORE", (Serializable) storeList);
            startActivity(intent);

        } else if (id == R.id.nav_order) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            intent.setClass(HomeActivity.this, HistoryActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);

        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            finish();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            LoginManager.getInstance().logOut();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void receiveData() {
        String key = null;
        user = (User) getIntent().getSerializableExtra("USER");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        else {

                            String key = task.getResult().getToken();
                            Client.getService().updateRegister(new Registrator(user, key)).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
    }

    private void initView() {
        rvStore = findViewById(R.id.rvStore);
        rvListFavorite = findViewById(R.id.rvListfavorite);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View infoView = navigationView.getHeaderView(0);

        TextView txtName = infoView.findViewById(R.id.txtUserName);
        TextView txtEmail = infoView.findViewById(R.id.txtUserEmail);
        TextView txtPoint = infoView.findViewById(R.id.txtPoint);
        txtName.setText(user.getFullname());
        txtEmail.setText(user.getAccountId().getEmail());
        txtList=findViewById(R.id.txtListFavorite);
        edtKey = findViewById(R.id.txtKey);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStore();

            }
        });

    }

    private void searchStore() {
        String key = edtKey.getText().toString();
        if (key.equalsIgnoreCase("") == false) {
            List<Store> result = new ArrayList<>();
            for (Store s : storeList) {
                if (s.getName().toUpperCase().contains(key.toUpperCase()) || s.getAddressList().get(0).toString().toUpperCase().contains(key.toUpperCase())) {
                    result.add(s);
                }
            }
            if(result.size()==0) Toasty.info(HomeActivity.this,"Không có kết quả",Toasty.LENGTH_SHORT).show();
            txtList.setText("Kết quả");
            listFavoriteAdapter = new StoreAdapter(this, user, result);
            rvListFavorite.setAdapter(listFavoriteAdapter);
            listFavoriteAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(int position) {
                    Intent intent = new Intent(HomeActivity.this, StoreActivity.class);
                    intent.putExtra("USER", user);
                    intent.putExtra("STORE", result.get(position));
                    startActivity(intent);
                }
            });

        }
    }

}
