package testing.study.test_target_nondefault_window;


import androidx.test.filters.LargeTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@LargeTest
public class ExampleInstrumentedTest {

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
    @Test
    public void useAppContext() {
    }
}
