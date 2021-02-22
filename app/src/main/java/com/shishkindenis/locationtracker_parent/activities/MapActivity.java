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

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{


//    Разобраться с extends FragmentActivity

//    @InjectPresenter
//    MapPresenter mapPresenter;

    private ActivityMapBinding activityMapBinding;

    String TAG = "Location";
    FirebaseFirestore firestoreDataBase;
    //    Убрать public
    public Double longitude = -34.0;
    public Double latitude = 151.0;
    public String time;
    private GoogleMap mMap;
    PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        View view = activityMapBinding.getRoot();
        setContentView(view);

        firestoreDataBase = FirebaseFirestore.getInstance();

        polylineOptions  = new PolylineOptions();

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

//        А ЕСЛИ ЧЕРЕЗ ТЕЛЕФОН?
//        firestoreDataBase.collection(EmailAuthActivity.userID)
//        firestoreDataBase.collection(EmailAuthPresenter.userID)
        firestoreDataBase.collection(MainActivity.userID)
                .whereEqualTo("Date", CalendarActivity.sDate)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(task.getResult().isEmpty()){
                            Log.d(TAG, "There is no track for chosen date");
                            showAlertDialog();
                        }
                    else{
                            for (QueryDocumentSnapshot document : task.getResult()) {
//Удалить

                                    Log.d(TAG, "Document exists");
                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                Вынести в отдельный метод
                                    longitude = (Double) document.get("Longitude");
                                    latitude = (Double) document.get("Latitude");
                                    time = (String) document.get("Time");

//                                Вынести в отдельный метод
                                    LatLng someplace = new LatLng(latitude, longitude);
                                    mMap.addMarker(new MarkerOptions().position(someplace).title(time));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));
                                    mMap.addPolyline(polylineOptions
//                                    .clickable(true)
                                            .color(Color.BLUE)
                                            .width(3)
                                            .add(new LatLng(latitude, longitude)));

                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("There is no track for chosen date")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(this, CalendarActivity.class);
                    intent.putExtra("abc9", "abc9");
                    startActivity(intent);
                })
                .show();
    }

 /*   private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();

    }

    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }*/
}