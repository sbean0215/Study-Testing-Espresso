package testing.study.test_target_nondefault_window;


import android.content.Intent;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import testing.study.test_target_nondefault_window.otheractivity.DisplayActivity;
import testing.study.test_target_nondefault_window.otheractivity.GestureActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OpenActivityTest2 {

    @Rule
    public ActivityTestRule<SendActivity> activityRule = new ActivityTestRule(SendActivity.class);

    @Before
    public void before() {
        Intents.init();
    }

    @Test
    public void openActivityTest4() {

        onView(withId(R.id.send_data_edit_text))
                .perform(typeText("!!!!!"));

        onView(withId(R.id.send_button))
                .perform(click());

        intended(hasComponent(DisplayActivity.class.getName()));
    }

    @Test
    public void openActivityTest5() {

        onView(withId(R.id.send_data_edit_text))
                .perform(typeText("!!!!!"));

        onView(withId(R.id.send_button))
                .perform(click());

        //error >> AssertionFailedWithCauseError: Wanted to match 1 intents. Actually matched 0 intents.
        // IntentMatcher: has component: has component with: class name: is "testing.study.test_target_nondefault_window.otheractivity.GestureActivity"
        intended(hasComponent(GestureActivity.class.getName()));

    }

}
