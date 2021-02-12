package com.shishkindenis.locationtracker_parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shishkindenis.locationtracker_parent.examples.CalendarActivity;
import com.shishkindenis.locationtracker_parent.examples.FirebaseCloudActivity;

import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

//    Попробуй CallBack для подгрузки сначала Data-затем отображения точки
//    Попробуй другой метод GEOCoder
//    Track the device location using periodic location requests.

//    Если локация изменилась-добавить новый маркер
    String TAG = "TAG";
    FirebaseFirestore db;
    public Double longitude = -34.0;
    public Double latitude = 151.0;
    TextView tvShowLocationMap;
    private GoogleMap mMap;
    Button btnGetLocation;
//    CalendarActivity calendarActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tvShowLocationMap = findViewById(R.id.tvShowLocationInTV);
        btnGetLocation = findViewById(R.id.btnGetLocation);

//        calendarActivity = new CalendarActivity();




        db = FirebaseFirestore.getInstance();

//        btnGetLocation.setOnClickListener(v -> readLocation());
        readLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

//        FirebaseCloudActivity firebaseCloudActivity = new FirebaseCloudActivity();

//        LatLng sydney = new LatLng(longitude, latitude);



    }

    public  void readLocation(){

        db.collection("locationMap")/*.orderBy("2021-03-12")*/
//                .whereEqualTo("Date","2021-01-12")
                .whereEqualTo("Date",CalendarActivity.sDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                tvShowLocationMap.setText(String.valueOf(document.get("Longitude")));
//
//                                Log.d(TAG, String.valueOf(document.get("Longitude")));
//                                Log.d(TAG, String.valueOf(document.get("Latitude")));
//                            orderBy
                                longitude = (Double) document.get("Longitude");
                                latitude = (Double) document.get("Latitude");

                                LatLng someplace = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(someplace).title("Marker in Someplace").snippet(latitude.toString() + "\n" + longitude.toString()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}