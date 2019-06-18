package com.example.dell.webservice.map;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.dell.webservice.R;
import com.example.dell.webservice.activity.MapActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapManager implements GoogleMap.OnMyLocationButtonClickListener {
    private static final String TAG = "MapManager";
    private Context context;
    private Location myLocation;
    private GoogleMap googleMap;
    private Marker myMarker;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;


    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }
    public Location getLocation(){
        return myLocation;
    }

    public MapManager(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        client = new FusedLocationProviderClient(context);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        configMap();
        configMyLocationButton();
        getMyLocation();

    }

    private void configMap() {

        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setInfoWindowAdapter(new UserInfoWindowAdapter(context));
    }

    private void configMyLocationButton() {
        try {
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (LocationUtil.isGpsOpen(context)) {
            return false;
        }

        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

        SettingsClient settingsClient = new SettingsClient(context);

        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                requestGpsOpen(e);
            }
        });

        return true;
    }

    private void requestGpsOpen(Exception e) {
        if (e instanceof ResolvableApiException) {
            try {
                ResolvableApiException rae = (ResolvableApiException) e;
                rae.startResolutionForResult((MapActivity) context, RequestCode.SETTINGS_GPS);
            } catch (IntentSender.SendIntentException sie) {
                sie.printStackTrace();
            }
        }
    }

    private void getMyLocation() {
        try {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null)
                        Log.d(TAG, "get mylocation null");
                    else {
                        Log.d(TAG, "My location " + location);
                        myLocation = location;
                        moveCameraToLocation();

                    }

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void moveCameraToLocation() {
        LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        googleMap.animateCamera(cameraUpdate);
    }

    private void showMyMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
        markerOptions.title("Gom");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        if (myMarker == null) {
            googleMap.addMarker(markerOptions);
        } else
            myMarker.setPosition(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));


    }

    private class UserInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private LayoutInflater inflater;

        public UserInfoWindowAdapter(Context context) {
            inflater = LayoutInflater.from(context);

        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return getView(marker);
        }

        private View getView(Marker marker) {
            View view = inflater.inflate(R.layout.view_item_user_info_window, null);
            TextView txtName = view.findViewById(R.id.txtName);
            TextView txtAddress = view.findViewById(R.id.txtAddress);
            txtName.setText(marker.getTitle());
            txtAddress.setText(marker.getSnippet());
            return view;
        }
    }

    public void registerLocationChange() {
        try {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult.getLastLocation() != null) {
                        myLocation = locationResult.getLastLocation();
                        //Log.d("abc", myLocation.getProvider());
                        // moveCameraToLocation();
                        // showMyMarker();

                    }
                }
            };
            client.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void unregisterLocationChange() {
        client.removeLocationUpdates(locationCallback);
    }



}