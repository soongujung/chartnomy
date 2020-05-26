package com.chartnomy.indicators.extensions;

import com.chartnomy.indicators.extensions.timer.method.MethodTimeoutExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 파라미터 있는 Extension 예제
 */
@SpringBootTest
@ActiveProfiles("testprod")
public class MethodTimerTest2 {

	@RegisterExtension
	MethodTimeoutExtension methodTimeoutExtension = new MethodTimeoutExtension(1000L);

	@DisplayName("메서드 시간 측정 예제 2")
	@Test
	public void test_method_timer() throws Exception{
		Thread.sleep(500);
	}
}
