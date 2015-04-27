package com.example.databaseexample;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sohammondal on 27/04/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyInstrumentationTests{
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void login_MainActivity_hasPassed() {
        onView(withId(R.id.activity_main_email)).perform(typeText("soham@triveous"));
        onView(withId(R.id.activity_main_password)).perform(typeText("alphabeta112233"));
        onView(withId(R.id.activity_main_submit)).perform(click());
        registerIdlingResources(new LoadMusicIdlingResource(mainActivityActivityTestRule));
        onView(withId(R.id.activity_main_listview)).check(matches(isDisplayed()));
    }

    public static class LoadMusicIdlingResource implements IdlingResource {
        private static final String NAME = "loadMusicResource";
        private ResourceCallback resourceCallback;
        ActivityTestRule<MainActivity> activityRule;

        public LoadMusicIdlingResource(ActivityTestRule<MainActivity> activityRule) {
            this.activityRule = activityRule;
        }

        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public boolean isIdleNow() {
            if (activityRule.getActivity().collection1List != null || activityRule.getActivity().isLoginFailure()) {
                resourceCallback.onTransitionToIdle();
                return true;
            }
            return false;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }


}
