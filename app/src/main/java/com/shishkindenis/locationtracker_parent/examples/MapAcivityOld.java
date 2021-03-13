package com.shishkindenis.locationtracker_parent.examples;

public class MapAcivityOld {
//    public class MapActivity extends FragmentActivity implements OnMapReadyCallback, MapView {
//
//        private static final String DATE_FIELD = "Date";
//        private static final String LONGITUDE_FIELD = "Longitude";
//        private static final String LATITUDE_FIELD = "Latitude";
//        private static final String TIME_FIELD = "Time";
//        private static final String TAG = "Location";
//        @InjectPresenter
//        MapPresenter mapPresenter;
//        @Inject
//        IdSingleton idSingleton;
//        private FirebaseFirestore firestoreDataBase;
//        private PolylineOptions polylineOptions;
//        private String date;
//        private ActivityMapBinding binding;
//        private GoogleMap map;
//        private Double longitude;
//        private Double latitude;
//        private String time;
//        private String userId;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            binding = ActivityMapBinding.inflate(getLayoutInflater());
//            View view = binding.getRoot();
//            setContentView(view);
//
//            firestoreDataBase = FirebaseFirestore.getInstance();
//            polylineOptions = new PolylineOptions();
//            date = getIntent().getExtras().getString(DATE_FIELD);
//            MyApplication.appComponent.inject(this);
//            userId = idSingleton.getUserId();
//
//            readLocation();
//            initMapsFragment();
//        }
//
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//
//            map = googleMap;
//            map.getUiSettings().setZoomControlsEnabled(true);
//            map.getUiSettings().setCompassEnabled(true);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            map.setMyLocationEnabled(true);
//            map.getUiSettings().setMyLocationButtonEnabled(true);
//        }
//
//        public void readLocation() {
//            firestoreDataBase.collection(userId)
//                    .whereEqualTo(DATE_FIELD, date)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            if (task.getResult().isEmpty()) {
//                                backToCalendarActivity();
//                            } else {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                    setResult(RESULT_OK, null);
//                                    getPosition(document);
//                                    setTrack(map);
//                                }
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    });
//        }
//
//        public void setTrack(GoogleMap mMap) {
//            LatLng someplace = new LatLng(latitude, longitude);
//            mMap.addMarker(new MarkerOptions().position(someplace).title(time));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(someplace));
//            mMap.addPolyline(polylineOptions
//                    .color(Color.BLUE)
//                    .width(3)
//                    .add(new LatLng(latitude, longitude)));
//        }
//
//        public void getPosition(QueryDocumentSnapshot document) {
//            longitude = (Double) document.get(LONGITUDE_FIELD);
//            latitude = (Double) document.get(LATITUDE_FIELD);
//            time = (String) document.get(TIME_FIELD);
//        }
//
//        public void backToCalendarActivity() {
//            setResult(RESULT_CANCELED, null);
//            finish();
//        }
//
//        public void initMapsFragment() {
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }
//    }
}