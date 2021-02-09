package com.shishkindenis.locationtracker_parent.examples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shishkindenis.locationtracker_parent.MapActivity;
import com.shishkindenis.locationtracker_parent.PhoneAuthActivity;
import com.shishkindenis.locationtracker_parent.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseCloudActivity extends AppCompatActivity {
    String TAG = "TAG";
    FirebaseFirestore db;
    Map<String, Object> user;

   public Double longitude = -34.0;
   public Double latitude = 151.0;

    TextView tvShowLocation;

    Button btnGet;
    Button btnGoToMap;

    List<String> listOfLocation = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_cloud);

        db = FirebaseFirestore.getInstance();

        user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        listOfLocation = new ArrayList<>();

        tvShowLocation = findViewById(R.id.tvShowLocation);
        btnGet = findViewById(R.id.btnGet);
//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readLocation();
//            }
//        });
        readLocation();

        btnGoToMap = findViewById(R.id.btnGoToMap);
        btnGoToMap.setOnClickListener(v -> {
                Intent intent = new Intent(this, MapActivity.class);
                intent.putExtra("abc6", "abc6");
                startActivity(intent);
        });

//        addData();
//        readData();



// Add a new document with a generated ID

    }

    public void addData(){
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public  void readData(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public  void readLocation(){
        db.collection("locationMap")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                tvShowLocation.setText(String.valueOf(document.get("Longitude")));

                                Log.d(TAG, String.valueOf(document.get("Longitude")));
                                Log.d(TAG, String.valueOf(document.get("Latitude")));

                                longitude = (Double) document.get("Longitude");
                                latitude = (Double) document.get("Latitude");


                            }

                                } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}