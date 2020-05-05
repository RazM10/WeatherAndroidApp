package org.myself.weather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewLocation;

    //Location Parameters
    String LOCATION_PROVIDERS = LocationManager.NETWORK_PROVIDER;
    LocationManager locationManager;
    LocationListener locationListener;
    final int REQUEST_CODE = 123;
    int MIN_TIME = 5000;
    float MIN_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLocation=findViewById(R.id.main_location_tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getCurrentLocation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location2) {
                Log.d("WeatherApp", "onLocationChanged(): callback received");

                String longitude = String.valueOf(location2.getLongitude());
                String latitude = String.valueOf(location2.getLatitude());

                Log.d("WeatherApp", "longitude: " + longitude);
                Log.d("WeatherApp", "latitude: " + latitude);

                textViewLocation.setText("Lat: "+latitude+ " long: "+longitude);
                //extra start here
//                double longi = location2.getLongitude();
//                double lati = location2.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("WeatherApp", "onPermissionDisable(): callback received");
            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDERS, MIN_TIME, MIN_DISTANCE, locationListener);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("WeatherApp", "onRequestPR(): granted");
                getCurrentLocation();
            } else {
                getCurrentLocation();
            }
        }
    }
}
