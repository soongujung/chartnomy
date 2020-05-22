package com.chartnomy.indicators.temp.custom.annotation;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import com.chartnomy.indicators.temp.custom.extension.CustomExtension;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apiguardian.api.API;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestTemplate
@ExtendWith(CustomExtension.class)
public @interface CustomAnnotation {
	String name() default "[{index}] {arguments}";
}
