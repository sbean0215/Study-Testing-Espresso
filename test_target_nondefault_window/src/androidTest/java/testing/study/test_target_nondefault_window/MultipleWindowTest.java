package testing.study.test_target_nondefault_window;


import android.content.Intent;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import testing.study.test_target_nondefault_window.otheractivity.DisplayActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MultipleWindowTest {

    /** 안드로이드는 다중창을 지원
     * 예를들어 검색 앱에서 자동완성 window 가 기본 window 위에 그려지는 경우
     *
     * 기본적으로 Espresso 는
     * 휴리스틱(발견적, 귀납적, 체험적, 직관적)을 사용하여
     * 상호 작용하려는 창을 추측.
     *
     * 일반적으로는 휴리스틱 방법으로 충분하나
     * 상호작용할 window 를 지정해야할 때도 있음
     *
     * 커스텀 root window matcher
     *  또는 기본 Root matcher 를 이용하여 할 수 있음.
     * */

    @Rule
    public ActivityTestRule<SendActivity> activityRule = new ActivityTestRule(SendActivity.class);

    @Test
    public void test() {
        /*
        inRoot()
         이 ViewInteraction 의 범위를 지정된 루트 매처가 선택한 루트로 만듦.

         withDecorView()
         주어진 뷰 매처와 일치하는 데코 뷰와 루트를 일치시킴.

         getActivity.getwindow.getDecoview()
         창 관리자에 창으로 추가 할 수있는
         최상위 창 장식보기
         (표준 창 프레임 / 장식 및 그 안의 클라이언트 내용 포함)
         를 검색.

         ==> WindowManager 로 LayoutParams 등을관리..

         아래 코드는 텍스트 자동완성 팝업을 테스트.
         해당 window는 창관리자에 추가 불가 .
         때문에 not is
        */
        onView(withText("South China Sea"))
                .inRoot(withDecorView(not(is(activityRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }

    //위 코드를 쪼개어 step by step 으로 기술
    @Test
    public void testInteractionsWithAutoCompletePopup() {
        // 처음에는 단 1개의 윈도우를 갖지만,
        // 자동완성 텍스트뷰에 타이핑을 하면, 다른 윈도우가 스크린의 top에 겹쳐진다.
        // 에스프레소는 키보드/ime 에 연결되지 않기 때문에, 이 레이어를 무시함.
        onView(withId(R.id.auto_complete_text_view))
                .perform(scrollTo())
                .perform(typeText("So"));

        // 보시다시피, 우리는 화면의 새 창에 알지 못하는 타이핑을 계속함.
        // So 를 검색했을때는
        // 2 개의 자동완성 텍스트 (South China Sea and Southern Ocean)가 있어야 함.
        // 그것을 "uth "를 더해서 한개로 좁힘.
        onView(withId(R.id.auto_complete_text_view))
                .perform(typeTextIntoFocusedView("uth "));

        // 검색된 텍스트를 명시적으로 탭하고 싶을 때
        // 에스프레소의 기본윈도우 선택 휴리스틱을 교체하여야 함
        onView(withText("South China Sea"))
                //팝업(스피너또는 자동완성샘플창) 인것을 ViewInteraction 범위로 지정.. (휴리스틱 교체)
                .inRoot(isPlatformPopup())
                .perform(click());

        // 탭 후 텍스트가 채워졌는지 확인
        onView(withId(R.id.auto_complete_text_view))
                .check(matches(withText("South China Sea")));

        // 검색 텍스트 클리어 하고 S 를 입력
        onView(withId(R.id.auto_complete_text_view))
                .perform(clearText())
                .perform(typeText("S"));

        // 자동 완성 상자는 ListView 를 사용하여 구현되므로
        // 이 대화 상자를 찾는 방법은 onData() .
        // inRoot 를 사용해도 된다.

        // 일부 자동완성 텍스트가 View 계층의 일부가 아닐수 있기 때문에,
        // Scroll() 을 사용하지 않는것이 유용할 수 있음.
        onData(allOf(instanceOf(String.class), is("Baltic Sea")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.auto_complete_text_view))
                .check(matches(withText("Baltic Sea")));
    }

}
