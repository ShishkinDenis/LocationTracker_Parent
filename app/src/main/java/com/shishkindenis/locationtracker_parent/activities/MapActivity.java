package com.shishkindenis.locationtracker_parent.activities;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityMapBinding;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

//    Разобраться с extends FragmentActivity

//    @InjectPresenter
//    MapPresenter mapPresenter;

    private ActivityMapBinding activityMapBinding;

    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase;
//    Убрать public
    public Double longitude = -34.0;
    public Double latitude = 151.0;
    public String time;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        View view = activityMapBinding.getRoot();
        setContentView(view);

        firestoreDataBase = FirebaseFirestore.getInstance();

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

    public  void readLocation(){

//        А ЕСЛИ ЧЕРЕЗ ТЕЛЕФОН?
//        firestoreDataBase.collection(EmailAuthActivity.userID)
        firestoreDataBase.collection(EmailAuthPresenter.userID)
                .whereEqualTo("Date",CalendarActivity.sDate)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
//Удалить
                            Log.d(TAG, document.getId() + " => " + document.getData());
//                                Вынести в отдельный метод
                            longitude = (Double) document.get("Longitude");
                            latitude = (Double) document.get("Latitude");
                            time = (String) document.get("Time");

//                                Вынести в отдельный метод
                            LatLng someplace = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(someplace).title(time));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}