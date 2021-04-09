package com.shishkindenis.locationtracker_parent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shishkindenis.locationtracker_parent.presenters.MapPresenter;
import com.shishkindenis.locationtracker_parent.singletons.DateSingleton;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.MapView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapPresenterTest {
    MapPresenter mapPresenter;
    @Mock
    FirebaseFirestore firestoreDataBase;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    DateSingleton dateSingleton;
    @Mock
    MapView$$State mapView$$State;
    @Mock
    Task task;
    @Mock
    CollectionReference collectionReference;
    @Mock
    Query query;

    @Before
    public void setUp() {
//        mapPresenter = new MapPresenter(firebaseUserSingleton,dateSingleton);
        mapPresenter = new MapPresenter(firebaseUserSingleton,dateSingleton,firestoreDataBase);
        mapPresenter.setViewState(mapView$$State);
    }
//    DELETE
    @Test
    public void errorGettingDocumentsIsCalled0() {
        String DATE_FIELD = "Date";
        String date = "2021-04-07";
        String userId = null;
        when(firestoreDataBase.collection(userId)).thenReturn(collectionReference);
        mapPresenter.readLocation();
        verify(firestoreDataBase).collection(userId);
    }

    @Test
    public void errorGettingDocumentsIsCalled() {
        String DATE_FIELD = "Date";
        String date = null;
        String userId = null;

        when(firestoreDataBase.collection(userId)).thenReturn(collectionReference);
        when(firestoreDataBase.collection(userId).whereEqualTo(DATE_FIELD,date)).thenReturn(query);
        when(firestoreDataBase.collection(userId).whereEqualTo(DATE_FIELD,date).get()).thenReturn(task);
        mapPresenter.readLocation();

        ArgumentCaptor<OnCompleteListener<AuthResult>> listenerCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        verify(task).addOnCompleteListener(listenerCaptor.capture());

        OnCompleteListener<AuthResult> onCompleteListener = listenerCaptor.getValue();
        onCompleteListener.onComplete(task);
        verify(mapView$$State).showToast(R.string.error_getting_documents);
    }


}
