package com.chartnomy.indicators.temp.custom.extension;


import static org.junit.platform.commons.util.AnnotationUtils.isAnnotated;

import com.chartnomy.indicators.temp.custom.annotation.CustomAnnotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.aggregator.AggregationUtils;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.Preconditions;

public class CustomExtension implements TestTemplateInvocationContextProvider {

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		System.out.println(" === supportsTestTemplate === ");
		if (!context.getTestMethod().isPresent()){
			return false;
		}

		Method testMethod = context.getTestMethod().get();
		if (!isAnnotated(testMethod, CustomAnnotation.class)){
			return false;
		}

		Preconditions.condition(AggregationUtils.hasPotentiallyValidSignature(testMethod),
			() -> String.format(
				"@CustomAnnotation method [%s] declares formal parameters in an invalid order: "
					+ "argument aggregators must be declared after any indexed arguments "
					+ "and before any arguments resolved by another ParameterResolver.",
				testMethod.toGenericString()));

		return true;
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
		ExtensionContext context) {
		System.out.println(" === provideTestTemplateInvocationContexts === ");
		return null;
	}
}
