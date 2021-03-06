package com.shishkindenis.locationtracker_parent.presenters;

import com.shishkindenis.locationtracker_parent.views.MapView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
//    final static String LONGITUDE_FIELD = "Longitude";
//    final static String LATITUDE_FIELD = "Latitude";
//    final static String TIME_FIELD = "Time";
//    PolylineOptions polylineOptions = new PolylineOptions();
//    private Double longitude;
//    private Double latitude;
//    private String time;
//    final static String DATE_FIELD = "Date";
//    private final String TAG = "Location";


    public MapPresenter() {
    }

//    public void readLocation(GoogleMap mMap) {
//        FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
//        firestoreDataBase.collection(MainActivity.userID)
//                .whereEqualTo(DATE_FIELD, CalendarActivity.getDate())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        if (task.getResult().isEmpty()) {
//                            showAlertDialog();
//                        } else {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                getPosition(document);
//                                setTrack(mMap);
//                            }
//                        }
//                    } else {
//                        Log.w(TAG, "Error getting documents.", task.getException());
//                    }
//                });
//    }

//    public void setTrack(GoogleMap mMap) {
//        LatLng someplace = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(someplace).title(time));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));
//        mMap.addPolyline(polylineOptions
//                .color(Color.BLUE)
//                .width(3)
//                .add(new LatLng(latitude, longitude)));
//    }

//    public void getPosition(QueryDocumentSnapshot document) {
//        longitude = (Double) document.get(LONGITUDE_FIELD);
//        latitude = (Double) document.get(LATITUDE_FIELD);
//        time = (String) document.get(TIME_FIELD);
//    }
}
