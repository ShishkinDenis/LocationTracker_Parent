package com.shishkindenis.locationtracker_parent.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.databinding.ActivityMapBinding;
import com.shishkindenis.locationtracker_parent.presenters.MapPresenter;
import com.shishkindenis.locationtracker_parent.views.MapView;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MapActivity extends MvpAppCompatActivity implements OnMapReadyCallback, MapView {

    private static final String LONGITUDE_FIELD = "Longitude";
    private static final String LATITUDE_FIELD = "Latitude";
    private static final String TIME_FIELD = "Time";
//    @InjectPresenter
//    MapPresenter mapPresenter;

    @Inject
    @InjectPresenter
    MapPresenter mapPresenter;

    @ProvidePresenter
    MapPresenter provideMapPresenter(){return mapPresenter;}

    private PolylineOptions polylineOptions;
    private ActivityMapBinding binding;
    private GoogleMap map;
    private Double longitude;
    private Double latitude;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.appComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        polylineOptions = new PolylineOptions();
        mapPresenter.readLocation();
        initMapsFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void setTrack() {
        LatLng someplace = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(someplace).title(time));
        map.moveCamera(CameraUpdateFactory.newLatLng(someplace));
        map.addPolyline(polylineOptions
                .color(Color.BLUE)
                .width(3)
                .add(new LatLng(latitude, longitude)));
    }

    public void getPosition(QueryDocumentSnapshot document) {
        longitude = (Double) document.get(LONGITUDE_FIELD);
        latitude = (Double) document.get(LATITUDE_FIELD);
        time = (String) document.get(TIME_FIELD);
    }

    public void backToCalendarActivityWithCancelledResult() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    public void initMapsFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void backToCalendarActivityWithOkResult() {
        setResult(RESULT_OK, null);
    }
}
