package com.krish_the_dev.mapexample;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected GoogleMap mMap;
    private String[] requiredPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(
                this,
                requiredPermissions,
                PackageManager.PERMISSION_GRANTED
        );

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, requiredPermissions, PackageManager.PERMISSION_GRANTED);

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new CustomLocationListener());
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng aubit = new LatLng(10.79, 78.135120);
        mMap.addMarker(new MarkerOptions().position(aubit).title("Trichy"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(aubit));
    }
}

class CustomLocationListener extends MapsActivity implements LocationListener {

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }
}
