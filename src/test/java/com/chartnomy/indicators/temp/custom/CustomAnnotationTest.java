package com.chartnomy.indicators.temp.custom;

import com.chartnomy.indicators.temp.custom.annotation.CustomAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testprod")
public class CustomAnnotationTest {

	@CustomAnnotation
	@ValueSource(strings = {"a", "b", "c"})
//	@ParameterizedTest
//	@CsvSource("")
	void testCustomAnnotation(){
		System.out.println("======= testCustomAnnotation =======");
	}
}
