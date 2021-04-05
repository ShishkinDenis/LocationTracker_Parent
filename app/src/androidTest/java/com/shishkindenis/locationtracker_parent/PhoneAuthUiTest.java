package com.shishkindenis.locationtracker_parent;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.activities.MainActivity;
import com.shishkindenis.locationtracker_parent.activities.PhoneAuthActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhoneAuthUiTest {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Test
    public void instanceOfPhoneActivityIsSavedWhenRotating() {
//сначала выйти в before
        ActivityTestRule<PhoneAuthActivity> phoneAuthActivityActivityTestRule = new ActivityTestRule<>(PhoneAuthActivity.class);
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etPhoneNumber)).perform(replaceText("+79998887766"));
        phoneAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        onView(withId(R.id.etPhoneNumber)).check(matches(withText("+79998887766")));
    }

    @Test
    public void invalidPhoneNumberErrorIsDisplayed(){
        ActivityTestRule<PhoneAuthActivity> phoneAuthActivityActivityTestRule = new ActivityTestRule<>(PhoneAuthActivity.class);
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnRequestCode)).perform(click());
        onView(withId(R.id.etPhoneNumber)).check(matches(hasErrorText("Invalid phone number")));
    }

    @Test
    public void invalidCodeErrorIsDisplayed(){
        ActivityTestRule<PhoneAuthActivity> phoneAuthActivityActivityTestRule = new ActivityTestRule<>(PhoneAuthActivity.class);
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnRequestCode)).perform(click());
        onView(withId(R.id.etVerificationCode)).check(matches(hasErrorText("Invalid code")));
    }

    @Test
    public void phoneAuthenticationSuccessful() {
//        включить интернет
        auth.signOut();
        String phoneNumber = "+75558780743";
        String verificationCode = "123007";
        ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
        MainActivity mainActivity = mainActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnPhone)).perform(click());

        onView(withId(R.id.etPhoneNumber)).perform(replaceText(phoneNumber));
        onView(withId(R.id.btnRequestCode)).perform(click());
//        сделать временно активной?
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.etVerificationCode)).perform(replaceText(verificationCode));
        onView(withId(R.id.btnVerifyCode)).perform(click());

        onView(withText("Authentication successful")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void phoneAuthenticationFailed() {
//        включить интернет
//        нужен ли signOut?
        auth.signOut();
        String phoneNumber = "+75558780743";
        String verificationCode = "000000";
        ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
        MainActivity mainActivity = mainActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnPhone)).perform(click());

        onView(withId(R.id.etPhoneNumber)).perform(replaceText(phoneNumber));
        onView(withId(R.id.btnRequestCode)).perform(click());
//        сделать временно активной?
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.etVerificationCode)).perform(replaceText(verificationCode));
        onView(withId(R.id.btnVerifyCode)).perform(click());

        onView(withText("Authentication failed")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

//    Firebase emulator
//    Firebase TestLab
//    Tracking sensitivity - 60 meters (10 for test), period each 10 min (5 second for test).
//    authentication successful
//    authentication failed
}
