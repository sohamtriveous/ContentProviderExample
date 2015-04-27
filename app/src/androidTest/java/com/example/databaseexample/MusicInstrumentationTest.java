package com.example.databaseexample;


import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sohammondal on 25/04/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void login_MainActivity() {
        onView(withId(R.id.activity_main_email)).perform(typeText("soham@triveous.com"));
        onView(withId(R.id.activity_main_password)).perform(typeText("alpha123"));
        onView(withId(R.id.activity_main_submit)).perform(click());
        registerIdlingResources(new loadApiResource(mActivityRule));
        onView(withId(R.id.activity_main_listview)).check(matches(isDisplayed()));
    }

    /**
     * A resource that waits for the network task to complete before it can let the other tests to proceed
     */
    public static class loadApiResource implements IdlingResource {
        private ResourceCallback resourceCallback;
        private ActivityTestRule<MainActivity> mActivityRule;
        private static final String NAME = "loadApiResource";

        public loadApiResource(ActivityTestRule<MainActivity> mActivityRule) {
            this.mActivityRule = mActivityRule;
        }

        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public boolean isIdleNow() {
            // login was attempted, lets wait for collection1List to populate
            if (mActivityRule.getActivity().collection1List != null) {
                resourceCallback.onTransitionToIdle();
                return true;
            } else {
                // login was tried but was a failure (for any reason: username/password/network), we need to move on
                if(mActivityRule.getActivity().isLoginFailure()) {
                    resourceCallback.onTransitionToIdle();
                    return true;
                }
            }
            return false;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }
}
