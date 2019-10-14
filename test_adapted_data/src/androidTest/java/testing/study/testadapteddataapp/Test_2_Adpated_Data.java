package testing.study.testadapteddataapp;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*;

public class Test_2_Adpated_Data {
    /*  ActionBarTestActivity.class
     *  일반 작업표시줄 (normal action bar)
     *  상황에 맞는 작업 표시줄(contextual action bar) <-- SupportActionMode
     *  두종류 액션바에 대한 테스트 */

    @Rule
    public ActivityTestRule<LongListActivity> activityRule = new ActivityTestRule<>(LongListActivity.class);

    @Test
    public void testDataItemNotInAdapter() {
        // 특정 데이터 항목이
        // AdapterView 내에 "있지 않음" 증명하려는 경우

        // 해당AdapterView를 찾아 보유 데이터를 조사해야 함.
        // onData ()를 사용할 필요는 없고,

        // 대신 onView ()를 사용하여 AdapterView를 찾은 다음
        // 커스텀 매처를 사용하여 뷰 내부의 데이터를 테스트.

        onView(withId(R.id.list))
                .check(matches(not(withAdaptedData(withItemContent("item: 168")))));
    }

    /** testWithAdaptedData() 를 위한 커스텀 매쳐
     *  matchesSafely의 인자로는 id가 list 인 view 가 전달됨?? --> onView 로 부터 시작하기때문에.
     *  AdapterView 를 찾고 다른 Matcher 가 그 안의 데이터를 조사하도록 함*/
    private static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View item) {
                if(!(item instanceof AdapterView))  return false;

                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView)item).getAdapter();

                for(int i = 0 ; i < adapter.getCount() ; i++) {
                    if(dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    /** AdapterView 의 항목을 특정 문자열과 일치시킴.
     * AdapterView 의 항목은 문자열이어야함 */
    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    public static Matcher<Object> withItemContent(final Matcher<String> itemTextMatcher) {
        checkNotNull(itemTextMatcher);
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            public boolean matchesSafely(String text) {
                return itemTextMatcher.matches(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with item content: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }

}


