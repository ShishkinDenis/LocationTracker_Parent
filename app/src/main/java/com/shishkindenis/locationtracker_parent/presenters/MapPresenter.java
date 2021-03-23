package com.shishkindenis.locationtracker_parent.presenters;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shishkindenis.locationtracker_parent.singletons.DateSingleton;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.MapView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    private static final String DATE_FIELD = "Date";
    private static final String TAG = "Location";
//    @Inject
//    FirebaseUserSingleton firebaseUserSingleton;
//    @Inject
//    DateSingleton dateSingleton;
FirebaseUserSingleton firebaseUserSingleton;
    DateSingleton dateSingleton;


    private FirebaseFirestore firestoreDataBase;
    private String date;
    private String userId;

//    public MapPresenter() {
//        MyApplication.appComponent.inject(this);
//    }

    @Inject
    public MapPresenter(FirebaseUserSingleton firebaseUserSingleton, DateSingleton dateSingleton) {
      this.firebaseUserSingleton = firebaseUserSingleton;
      this.dateSingleton = dateSingleton;
    }

    public void readLocation() {
        firestoreDataBase = FirebaseFirestore.getInstance();
        userId = firebaseUserSingleton.getUserId();
        date = dateSingleton.getDate();

        firestoreDataBase.collection(userId)
                .whereEqualTo(DATE_FIELD, date)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            getViewState().backToCalendarActivityWithCancelledResult();
                        } else {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                getViewState().backToCalendarActivityWithOkResult();
                                getViewState().getPosition(document);
                                getViewState().setTrack();
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

}
