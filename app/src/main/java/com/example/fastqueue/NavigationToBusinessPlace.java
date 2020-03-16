package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.LocaleList;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class NavigationToBusinessPlace extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private BusinessAddress businessAddress;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_LOCATION_CODE = 1000;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_to_business_place);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }


    CallBack_MapReady myCallback = new CallBack_MapReady() {
        @Override
        public void setMapLocation(LatLng location) {
            mMap.clear();
            if (location.latitude == 1 && location.longitude == 1) {
                mMap.addMarker(new MarkerOptions().position(location).title("Location not found. Go fish"));
            } else {
                mMap.addMarker(new MarkerOptions().position(location));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(NavigationToBusinessPlace.this, MenuActivityClient.class);
        backIntent.putExtra(Constants.KEY_BUSINESS_LOCATION, true);
        startActivity(backIntent);
        NavigationToBusinessPlace.this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng currentLocation;
        // Add a marker in Sydney and move the camera
        currentLocation = new LatLng(businessAddress.getLatitude(), businessAddress.getLongitude());

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("בית העסק"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 7.5f));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }


    private void getLocation() {
        //check permission of the user to his current location
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            return;
        }
        //set location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    lat = currentLocation.getLatitude();
                    lon = currentLocation.getLongitude();

                } else {
                    lat = 1;
                    lon = 1;
                }
            }
        });
    }

}

