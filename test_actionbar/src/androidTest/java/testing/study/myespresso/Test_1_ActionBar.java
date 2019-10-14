package testing.study.myespresso;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test_1_ActionBar {

    /*  ActionBarTestActivity.class
     *  일반 작업표시줄 (normal action bar)
     *  상황에 맞는 작업 표시줄(contextual action bar) <-- SupportActionMode
     *  두종류 액션바에 대한 테스트 */

    @Rule
    public ActivityTestRule<ActionBarTestActivity> activityRule = new ActivityTestRule<>(ActionBarTestActivity.class);

    @Test
    public void testClickActionBarItem() {
        // ActionMode 숨기는 버튼 클릭
        onView(withId(R.id.hide_contextual_action_bar))
                .perform(click());

        onView(withId(R.id.action_save))
                .perform(click());

        // 클릭한 것을 텍스트로 보여주는 view 가 정상적으로 출력되는지 확인
        onView(withId(R.id.text_action_bar_result))
                .check(matches(withText("Save")));
    }

    @Test
    public void testClickActionModeItem() {
        // ActionMode 보여주는 버튼 클릭
        onView(withId(R.id.show_contextual_action_bar))
                .perform(click());

        onView((withId(R.id.action_lock)))
                .perform(click());

        // 클릭한 것을 텍스트로 보여주는 view 가 정상적으로 출력되는지 확인
        onView(withId(R.id.text_action_bar_result))
                .check(matches(withText("Lock")));
    }

    @Test
    public void testActionBarOverflow() {
        // 액션바에서 숨김 처리되어 오버플로 메뉴(점세개??) 를 누르면 나타나는
        // R.id.action_search
        // R.id.action_world
        // 두개에 대한 테스트

        // Action Mode 제거
        onView(withId(R.id.hide_contextual_action_bar))
                .perform(click());

        // 기기가 물리 메뉴버튼 또는 소프트웨어 메뉴버튼을 가지고있는지에 따라
        // 옵션메뉴 또는 오버플로우 메뉴를 연다,
        openActionBarOverflowOrOptionsMenu(
                ApplicationProvider.getApplicationContext());

        //화면상 보이는게 텍스트라서 id 보다는 텍스트로 찾은것 같다.
        onView(withText("World"))
                .perform(click());

        // 클릭한 것을 텍스트로 보여주는 view 가 정상적으로 출력되는지 확인
        onView(withId(R.id.text_action_bar_result))
                .check(matches(withText("World")));
    }

    public void testActionModeOverflow() {
        // Action Mode 설정
        onView(withId(R.id.show_contextual_action_bar))
                .perform(click());

        // 액션모드의 오버플로메뉴를 연다
        openContextualActionModeOverflowMenu();

        onView(withText("Key"))
                .perform(click());

        // 클릭한 것을 텍스트로 보여주는 view 가 정상적으로 출력되는지 확인
        onView(withId(R.id.text_action_bar_result))
                .check(matches(withText("Key")));
    }

    public void testDisplay() {
        /*
            보이는지 확인하려면
            onView(아이디 또는 텍스트)
            .check(matches(isDisplayed())))

            안보이는지 확인하려면
            .check(matches(not(isDisplayed()))))

            그런데 위의 경우는
            구조상에는 있고, 보이지 않는 경우(invisible) 경우만 해당한다.
            구조상 없어서 보이지 않는 경우(gone)의 테스트는 doesNotExist()를 사용

            gone 과 invisible 케이스를 추가 테스트 팔요

         */
    }

}
