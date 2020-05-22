package com.chartnomy.indicators.temp.custom;

import com.chartnomy.indicators.temp.custom.annotation.CustomAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testprod")
public class CustomAnnotationTest {

	@CustomAnnotation
	void testCustomAnnotation(){
		System.out.println("======= testCustomAnnotation =======");
	}
}
