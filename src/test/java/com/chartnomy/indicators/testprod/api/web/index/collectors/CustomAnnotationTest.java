package com.chartnomy.indicators.testprod.api.web.index.collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(TimingExtension.class)
@SpringBootTest
@ActiveProfiles("testprod")
public class CustomAnnotationTest {


	@BeforeEach
	void beforeEachSetup(){
		System.out.println("@BeforeEach");
	}

	@AfterEach
	void afterEachSetup(){
		System.out.println("@AfterEach");
	}

	@Test
	void testSample(){
		System.out.println("SAMPLE!!!");
	}
}
