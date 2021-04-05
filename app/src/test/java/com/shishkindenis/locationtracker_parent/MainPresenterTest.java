package com.shishkindenis.locationtracker_parent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.presenters.MainPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.MainView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    MainPresenter mainPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    MainView$$State mainView$$State;
    @Mock
    FirebaseAuth auth;
    @Mock
    FirebaseUser user;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(firebaseUserSingleton);
        mainPresenter.setViewState(mainView$$State);
    }

    @Test
    public void goToCalendarActivityForResultIsCalled() {
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(auth.getCurrentUser()).thenReturn(user);
        mainPresenter.checkIfUserLoggedIn();
        verify(mainView$$State).goToCalendarActivityForResult();
    }

}
