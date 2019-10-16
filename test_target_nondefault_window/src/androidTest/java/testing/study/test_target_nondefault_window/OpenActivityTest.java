package testing.study.test_target_nondefault_window;


import android.content.Intent;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import testing.study.test_target_nondefault_window.otheractivity.DisplayActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OpenActivityTest {

//    @Rule
//    public ActivityTestRule<SendActivity> activityRule = new ActivityTestRule(SendActivity.class);

    @Rule
    public ActivityTestRule<DisplayActivity> activityTestRule2
            = new ActivityTestRule(DisplayActivity.class, false, true);


    @Test
    public void openActivityTest() {
        Intent intent = new Intent();
        intent.putExtra(SendActivity.EXTRA_DATA, "your_value");

        activityTestRule2.launchActivity(intent);

        onView(withId(R.id.display_data))
                .check(matches(withText("your_value")));

    }

    @Test
    public void openActivityTest2() {
        //error ==> No views in hierarchy found matching: with id
        onView(withId(R.id.display_data))
                .check(matches(withText("!!!!!")));
    }

    @Test
    public void openActivityTest3() {
        onView(withId(R.id.send_data_edit_text))
                .perform(typeText("!!!!!"));

        onView(withId(R.id.send_button))
                .perform(click());

        //Display activity open

        onView(withId(R.id.display_data))
                .check(matches(withText("!!!!!")));
    }


}
