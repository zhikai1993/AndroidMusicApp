package com.android.flashbackmusic;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SortTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sortTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.refreshButton),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.refreshButton),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.buttonTitle), withText("Title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_content),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            ViewInteraction textView = onView(
                    allOf(withId(R.id.song_title),
                            childAtPosition(
                                    allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            childAtPosition(
                                                    allOf(withId(R.id.song_block),
                                                            childAtPosition(
                                                                    allOf(withId(R.id.song_mode), isDisplayed()),
                                                                    1)),
                                                    0)),
                                    0),
                            isDisplayed()));


            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.song_title),
                            childAtPosition(
                                    allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            childAtPosition(
                                                    allOf(withId(R.id.song_block),
                                                            childAtPosition(
                                                                    allOf(withId(R.id.song_mode), isDisplayed()),
                                                                    2)),
                                                    0)),
                                    0),
                            isDisplayed()));

            textView.check(matches(withText("Crystallik Strangers")));
            textView2.check(matches(withText("Dreaming of Home")));
        }
        catch (Exception e){}

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.buttonArtist), withText("Artist"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_content),
                                        1),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        try {

            ViewInteraction textView = onView(
                    allOf(withId(R.id.song_title),
                            childAtPosition(
                                    allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            childAtPosition(
                                                    allOf(withId(R.id.song_block),
                                                            childAtPosition(
                                                                    allOf(withId(R.id.song_mode), isDisplayed()),
                                                                    1)),
                                                    0)),
                                    0),
                            isDisplayed()));


            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.song_title),
                            childAtPosition(
                                    allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            childAtPosition(
                                                    allOf(withId(R.id.song_block),
                                                            childAtPosition(
                                                                    allOf(withId(R.id.song_mode), isDisplayed()),
                                                                    2)),
                                                    0)),
                                    0),
                            isDisplayed()));

            textView.check(matches(withText("Crystallik Strangers")));
            textView2.check(matches(withText("Dreaming of Home")));
        }
        catch (Exception e){}

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
