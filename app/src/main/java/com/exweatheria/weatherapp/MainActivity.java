package com.exweatheria.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exweatheria.weatherapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private ArrayList<PlaceModel> placesList;
    private ActivityMainBinding binding;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    LocationManager locationManager;
    String provider;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placesList = new ArrayList<>();
        try {
            getPlaceList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(),false);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // request Permission
            checkLocationPermission();
        }
        Location loc = locationManager.getLastKnownLocation(provider);
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    private void getPlaceList() throws IOException {
        InputStream csvData = getResources().openRawResource(R.raw.au);
        Scanner sc = new Scanner(csvData);
        Scanner line = null;
        if (sc.hasNext()) {
            // skip header line
            sc.nextLine();
        }
        int index = 0;
        while(sc.hasNextLine()){
            PlaceModel place = new PlaceModel();
            line = new Scanner(sc.nextLine());
            line.useDelimiter(",");
            while (line.hasNext()){
                String data = line.next();
                if(index == 0) {
                    place.setPlace(data);
                }
                else if(index == 1) {
                    place.setLatitude(data);
                }
                else if(index == 2) place.setLongitude(data);
                else if(index == 5) place.setState(data);
                index++;
            }
            index = 0;
            placesList.add(place);
        }
        csvData.close();
        sc.close();
    }
    public ArrayList<PlaceModel> getAllPlaces(){
        return placesList;
    }
    public String getCurrentLatitude(){
        Double truncatedDouble = BigDecimal.valueOf(latitude)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        return truncatedDouble.toString();
    }
    public String getCurrentLongitude(){
        Double truncatedDouble = BigDecimal.valueOf(longitude)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        return truncatedDouble.toString();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton("GRANT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}