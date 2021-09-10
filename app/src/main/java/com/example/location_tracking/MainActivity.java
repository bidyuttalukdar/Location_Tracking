package com.example.location_tracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btLocation,btSend,btMap;
    TextView v1, v2, v3, v4, v5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLocation = findViewById(R.id.get_location);
        v1 = findViewById(R.id.textviewforlocation1);
        v2 = findViewById(R.id.textviewforlocation2);
        v3 = findViewById(R.id.textviewforlocation3);
        v4 = findViewById(R.id.textviewforlocation4);
        v5 = findViewById(R.id.textviewforlocation5);
        btSend=findViewById(R.id.btSend);
        btMap=findViewById(R.id.btMap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }

            private void getLocation() {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                Task<Location> task = fusedLocationProviderClient.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            v1.setText(Html.fromHtml(
                                    "<font color='blue'><b>Latitude</b>:<br></font>"+
                                            location.getLatitude()
                            ));
                            v2.setText(Html.fromHtml(
                                    "<font color='blue'><b>Longitude</b>:<br></font>"+
                                            location.getLongitude()
                            ));
//                                v3.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Country</b>:<br></font>"+
//                                                location.getCountryName()
//                                ));
//                                v4.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Locality</b>:<br></font>"+
//                                                addresses.get(0).getLocality()
//                                ));
//                                v5.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Address</b>:<br></font>"+
//                                                addresses.get(0).getAddressLine(0)
//                                ));
                            btSend.setVisibility(View.VISIBLE);
                            btSend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseDatabase database;
                                    DatabaseReference databaseref;

                                    database=FirebaseDatabase.getInstance();
                                    databaseref=database.getReference("Location");
                                    databaseref.setValue(location.getLatitude(),location.getLongitude()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if(task.isComplete()){
                                                Toast.makeText(MainActivity.this,"Location Saved",Toast.LENGTH_SHORT);
                                                btSend.setVisibility(View.INVISIBLE);
                                            }else{
                                                Toast.makeText(MainActivity.this,"OOPs! Something went Wrong",Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });

//                                    LocationHelper helper=new LocationHelper(location.getLongitude(),
//                                            location.getLatitude()
////                                                addresses.get(0).getCountryName(),
////                                                addresses.get(0).getAddressLine(0),
////                                                addresses.get(0).getLocality()
////
//                                           );
//                                    FirebaseDatabase.getInstance().getReference("Current Location").setValue(helper)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                                    if(task.isComplete()){
//                                                        Toast.makeText(MainActivity.this,"Location is saved",Toast.LENGTH_SHORT);
//                                                        btSend.setVisibility(View.INVISIBLE);
//                                                    }else{
//                                                        Toast.makeText(MainActivity.this,"Oop! Something went wrong",Toast.LENGTH_SHORT);
//                                                    }
//                                                }
//                                            });
                                }
                            });

                        }
                    }
                });
//                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if(location!= null){
//                            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
//                            try {
//                                List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLatitude(),1);
//                                v1.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Latitude</b>:<br></font>"+
//                                                addresses.get(0).getLatitude()
//                                ));
//                                v2.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Longitude</b>:<br></font>"+
//                                                addresses.get(0).getLongitude()
//                                ));
//                                v3.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Country</b>:<br></font>"+
//                                                addresses.get(0).getCountryName()
//                                ));
//                                v4.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Locality</b>:<br></font>"+
//                                                addresses.get(0).getLocality()
//                                ));
//                                v5.setText(Html.fromHtml(
//                                        "<font color='blue'><b>Address</b>:<br></font>"+
//                                                addresses.get(0).getAddressLine(0)
//                                ));
//                                btSend.setVisibility(View.VISIBLE);
//                                btSend.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        LocationHelper helper=new LocationHelper(addresses.get(0).getLongitude(),
//                                                addresses.get(0).getLatitude(),
//                                                addresses.get(0).getCountryName(),
//                                                addresses.get(0).getAddressLine(0),
//                                                addresses.get(0).getLocality());
//                                        FirebaseDatabase.getInstance().getReference("Current Location").setValue(helper)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                                        if(task.isComplete()){
//                                                            Toast.makeText(MainActivity.this,"Location is saved",Toast.LENGTH_SHORT);
//                                                            btSend.setVisibility(View.INVISIBLE);
//                                                        }else{
//                                                            Toast.makeText(MainActivity.this,"Oop! Something went wrong",Toast.LENGTH_SHORT);
//                                                        }
//                                                    }
//                                                });
//                                    }
//                                });
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapViewOn.class));
            }
        });

    }
    public void LogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}