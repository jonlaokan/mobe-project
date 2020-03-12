package mobe.m2dl.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.faire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PhotoActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    4);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            findViewById(R.id.afficher).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                                    try {
                                        new FireUtils().writeImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/scanner/lol.jpg",location, new Runnable() {
                                            @Override
                                            public void run() {
                                            }
                                        });
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    MainActivity.this.startActivity(myIntent);
                                }
                            });

                        }
                    }
                });


    }


}
