package com.chartnomy.indicators.extensions;

import com.chartnomy.indicators.extensions.timer.method.MethodTimerExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testprod")
//@ExtendWith(MethodTimerExtension.class)
public class MethodTimerTest1 {

	@RegisterExtension
	MethodTimerExtension methodTimerExtension = new MethodTimerExtension();

	@DisplayName("메소드 시간측정 예제 1")
	@Test
	public void method_timer_test() throws Exception{
		Thread.sleep(1000);
		System.out.println(this);
	}
}
