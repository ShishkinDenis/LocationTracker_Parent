package com.shishkindenis.locationtracker_parent;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.shishkindenis.locationtracker_parent.activities.EmailAuthActivity;
import com.shishkindenis.locationtracker_parent.activities.PhoneAuthActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITests {
//    @Rule
//    public ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
//    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void instanceOfEmailActivityIsSavedWhenRotating() {
//сначала выйти в before
         ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(replaceText("sh-kin@mail.ru"));
       emailAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

        onView(withId(R.id.etEmail)).check(matches(withText("sh-kin@mail.ru")));
    }

    @Test
    public void instanceOfPhoneActivityIsSavedWhenRotating() {
//сначала выйти в before
        ActivityTestRule<PhoneAuthActivity> phoneAuthActivityActivityTestRulee = new ActivityTestRule<>(PhoneAuthActivity.class);
        PhoneAuthActivity phoneAuthActivity = phoneAuthActivityActivityTestRulee.launchActivity(new Intent());
        onView(withId(R.id.etPhoneNumber)).perform(replaceText("+79998887766"));
        phoneAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        onView(withId(R.id.etPhoneNumber)).check(matches(withText("+79998887766")));
    }
    @Test
    public void requiredEmailErrorIsDisplayed(){
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Required email")));
    }

    @Test
    public void requiredPasswordErrorIsDisplayed(){
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Required password")));
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
//    string resources
//  toast  "User with email: " + email + " was signed up "
//    toast "signed up failed"
//  toast  authentication_successful
//    toast authentication failed
//     выбранная дата в календаре сохранена при перевороте
//    кнопка verify isShown
//    проверить с емейл,но без пароля-комбинации
//    переворот calendarActivity
//    setError
//    toast calendarActivity
}
