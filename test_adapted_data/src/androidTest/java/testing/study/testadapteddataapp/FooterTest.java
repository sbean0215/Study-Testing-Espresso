package testing.study.testadapteddataapp;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

@LargeTest
public class FooterTest {
    @Rule
    public ActivityTestRule<LongListActivity> testRule = new ActivityTestRule<>(LongListActivity.class);

    // 리스트뷰에 addHeaderView() 또는 addFooterView()를 써서
    // 헤더 또는 푸터를 추가하였을때 테스트 하는 방법


    // 먼저 푸터임를 찾는 매쳐 만들어줌
    @SuppressWarnings("unchecked")
    public static Matcher isFooter() {
        return allOf(is(instanceOf(String.class)), is(LongListActivity.FOOTER));
//        return is(LongListActivity.FOOTER);
    }

    /*
    @SuppressWarnings("unchecked")
    public static Matcher<Object> isFooter() {
        return allOf(is(instanceOf(String.class)), is(LongListActivity.FOOTER));
    }
    */

    @Test
    public void testClickFooter() {
        onData(isFooter())
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("100")));
    }



}
