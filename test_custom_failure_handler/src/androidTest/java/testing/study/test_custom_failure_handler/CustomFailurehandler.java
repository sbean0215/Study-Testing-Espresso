package testing.study.test_custom_failure_handler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.base.DefaultFailureHandler;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.setFailureHandler;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@LargeTest
public class CustomFailurehandler {


    private static final String TAG = "CustomFailureHandlerTest";

    /* Espresso의 기본 FailureHandler 를
       커스텀 해서 사용하면
       스크린 샷 작성 또는 추가 디버그 정보 전달과 같은
       추가 또는 다른 오류 처리가 가능
       (스크린 샷 작성은 또다른 처리가 필요) */

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    /**
     * 이 실패 핸들러는 NoMatchingViewException 대신
     * CustomException 발생시키고
     * 다른 모든 실패를 DefaultFailureHandler 에 위임
     *
     * CustomFailureHandler 는
     * 테스트의 setUp () 메소드에서
     * Espresso 로 등록 할 수 있습니다.*/

    private static class CustomFailureHandler implements FailureHandler {
        private final FailureHandler delegate;

        public CustomFailureHandler(Context targetContext) {
            delegate = new DefaultFailureHandler(targetContext);
        }

        @Override
        public void handle(Throwable error, Matcher<View> viewMatcher) {
            try {
                delegate.handle(error, viewMatcher);
            } catch (NoMatchingViewException e) {
                throw new CustomException(e);
            }
        }
    }

    static class CustomException extends RuntimeException {
        CustomException(Throwable cause) {
            super(cause);
        }
    }

    @Before
    public void setUp() {
        activityRule.getActivity();
        setFailureHandler(new CustomFailureHandler(
                ApplicationProvider.getApplicationContext()));
    }

    /** Test Run 은 통과되고, Logcat 에서 다음 에러 로그를 볼 수 있다.
     * 예외처리 부분을 지우면, Test Cass 는 실패한다*/
    @Test
    public void testWithCustomFailureHandler() {
        try {
            onView(withText("does not exist")).perform(click());
        } catch (CustomFailurehandler.CustomException expected) {
            Log.e(TAG, "Special exception is special and expected: ", expected);
        }
    }

}


