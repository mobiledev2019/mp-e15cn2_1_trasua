package com.example.dell.webservice.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.webservice.adapter.DistanceStoreAdapter;
import com.example.dell.webservice.R;
import com.example.dell.webservice.map.LocationUtil;
import com.example.dell.webservice.map.MapManager;
import com.example.dell.webservice.map.RequestCode;
import com.example.dell.webservice.model.DistanceStore;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MapActivity extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private MapManager mapManager;
    private User user;
    private List<Store> storelist;
    private Location myLocation;
    private List<DistanceStore> distanceStores;
    private RecyclerView rvNearStore;
    private DistanceStoreAdapter distanceStoreAdapter;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        receiveData();
        initViews();
        listennerHander();
        initComponents();

    }

    private void receiveData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");
        storelist = (List<Store>) intent.getSerializableExtra("STORE");

    }

    private void initViews() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        rvNearStore = findViewById(R.id.rvNearByStore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvNearStore.setLayoutManager(linearLayoutManager);

    }

    private void initComponents() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapManager = new MapManager(MapActivity.this, googleMap);
                mapManager.registerLocationChange();
                for (Store s : storelist) {
                    googleMap.addMarker(new MarkerOptions().title(s.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                            .snippet(s.getAddressList().get(0).getDescription())
                            .position(new LatLng(Double.parseDouble(s.getAddressList().get(0).getLatitude()), Double.parseDouble(s.getAddressList().get(0).getLongtitude()))));
                }

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (mapManager.getLocation() != null) {
                                myLocation = mapManager.getLocation();
                                Double lat1 = myLocation.getLatitude();
                                Double long1 = myLocation.getLongitude();
                                Double lat2;
                                Double long2;
                                distanceStores = new ArrayList<>();
                                for (Store s : storelist) {
                                    lat2 = Double.parseDouble(s.getAddressList().get(0).getLatitude());
                                    long2 = Double.parseDouble(s.getAddressList().get(0).getLongtitude());
                                    Double distance = LocationUtil.distanceBetween2Points(lat1, long1, lat2, long2);
                                    DistanceStore d = new DistanceStore();
                                    d.setDistance(distance);
                                    d.setStore(s);
                                    distanceStores.add(d);
                                }
                                Collections.sort(distanceStores);
                                handler.sendEmptyMessage(101);
                                break;
                            }
                        }
                    }
                };
                new Thread(runnable).start();
            }
        });


    }

    private void listennerHander() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:
                        distanceStoreAdapter = new DistanceStoreAdapter(MapActivity.this, user, distanceStores);
                        rvNearStore.setAdapter(distanceStoreAdapter);
                        distanceStoreAdapter.setOnItemClickListener(new DistanceStoreAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClicked(int position) {
                                Store store = distanceStores.get(position).getStore();
                                Intent intent = new Intent();
                                intent.setClass(MapActivity.this, StoreActivity.class);
                                intent.putExtra("USER", user);
                                intent.putExtra("STORE", store);
                                startActivity(intent);
                            }
                        });

                }

            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RequestCode.SETTINGS_GPS:
                if (LocationUtil.isGpsOpen(this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mapManager.onMyLocationButtonClick();
                        }
                    }, 3000);
                } else {
                    Toast.makeText(this, "Please turn on GPS for use this feature", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapManager != null) {
            mapManager.registerLocationChange();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapManager != null) {
            mapManager.unregisterLocationChange();
        }
    }


}