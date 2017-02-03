package edu.sc.cse;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)


/**
 * Created by scuellar on 2/3/17.
 *
 * test types an email and password that has already been created and uses sign in button to sign.
 *
 *
 */


public class LoginScreenTest
{
    @Rule
    public ActivityTestRule<LoginScreen> mActivityTestRule = new ActivityTestRule<>(LoginScreen.class);

    @Test
    public void loginScreenTest() {


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email_field),
                        withParent(allOf(withId(R.id.activity_login_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("gamecockstudy@aol.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email_field), withText("gamecockstudy@aol.com"),
                        withParent(allOf(withId(R.id.activity_login_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password_field),
                        withParent(allOf(withId(R.id.activity_login_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("gamecocks"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password_field), withText("gamecocks"),
                        withParent(allOf(withId(R.id.activity_login_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in_button), withText("Sign In"),
                        withParent(allOf(withId(R.id.activity_login_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());


    }


    //THis is a change


}
