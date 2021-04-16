package com.krish_the_dev.mapexample;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public final String TAG = "MapsActivity";

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
            }

            LocationListener locationListener = new CustomLocationListener();

            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
                Log.d(TAG, "WTF1");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Log.d(TAG, "WTF2");
            }
        } catch (SecurityException se) {
            Log.e(TAG, "SHIT", se);
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
    public void onLocationChanged(Location location) {
        Log.d(TAG, "PLease work");
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
        Log.d(TAG, "PLease work");
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
