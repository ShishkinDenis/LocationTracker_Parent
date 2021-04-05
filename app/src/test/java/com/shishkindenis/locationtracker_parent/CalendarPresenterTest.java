package com.shishkindenis.locationtracker_parent;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.presenters.CalendarPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.CalendarView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalendarPresenterTest {
    CalendarPresenter calendarPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    CalendarView$$State calendarView$$State;
    @Mock
    FirebaseAuth auth;

    @Before
    public void setUp() {
        calendarPresenter = new CalendarPresenter(firebaseUserSingleton);
        calendarPresenter.setViewState(calendarView$$State);
    }

    @Test
    public void signOutIsCalled() {
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        calendarPresenter.signOut();
        verify(calendarView$$State).showToast(R.string.sign_out_successful);
    }
}
