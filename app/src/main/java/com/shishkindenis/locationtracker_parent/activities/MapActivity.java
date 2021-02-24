package com.shishkindenis.locationtracker_parent.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityMapBinding;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {


//    Разобраться с extends FragmentActivity

//    @InjectPresenter
//    MapPresenter mapPresenter;

    FirebaseFirestore firestoreDataBase;
    PolylineOptions polylineOptions;
    private final String TAG = "Location";
    private Double longitude;
    private Double latitude;
    private String time;
    private ActivityMapBinding activityMapBinding;
    private GoogleMap mMap;
    final static String LONGITUDE_FIELD = "Longitude";
    final static String LATITUDE_FIELD = "Latitude";
    final static String TIME_FIELD = "Time";
    final static String DATE_FIELD = "Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        View view = activityMapBinding.getRoot();
        setContentView(view);

        firestoreDataBase = FirebaseFirestore.getInstance();
        polylineOptions = new PolylineOptions();
        readLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void readLocation() {
        firestoreDataBase.collection(MainActivity.getUserID())
                .whereEqualTo(DATE_FIELD, CalendarActivity.getDate())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            showAlertDialog();
                        } else {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                getPosition(document);
                                setTrack();
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.there_is_no_track)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    Intent intent = new Intent(this, CalendarActivity.class);
                    startActivity(intent);
                })
                .show();
    }

    public void setTrack(){
        LatLng someplace = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(someplace).title(time));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));
        mMap.addPolyline(polylineOptions
                .color(Color.BLUE)
                .width(3)
                .add(new LatLng(latitude, longitude)));
    }

    public void getPosition(QueryDocumentSnapshot document){
        longitude = (Double) document.get(LONGITUDE_FIELD);
        latitude = (Double) document.get(LATITUDE_FIELD);
        time = (String) document.get(TIME_FIELD);
    }

}