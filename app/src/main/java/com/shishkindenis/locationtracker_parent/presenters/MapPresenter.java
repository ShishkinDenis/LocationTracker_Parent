package com.shishkindenis.locationtracker_parent.presenters;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shishkindenis.locationtracker_parent.R;
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
    FirebaseUserSingleton firebaseUserSingleton;
    DateSingleton dateSingleton;
    FirebaseFirestore firestoreDataBase;
    private String date;
    private String userId;

    @Inject
    public MapPresenter(FirebaseUserSingleton firebaseUserSingleton, DateSingleton dateSingleton, FirebaseFirestore firestoreDataBase) {
        this.firebaseUserSingleton = firebaseUserSingleton;
        this.dateSingleton = dateSingleton;
        this.firestoreDataBase = firestoreDataBase;
    }

    public void readLocation() {
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
                        getViewState().showToast(R.string.error_getting_documents);
                    }
                });

    }

}
