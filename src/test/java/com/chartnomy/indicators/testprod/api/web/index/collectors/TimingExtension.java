package com.chartnomy.indicators.testprod.api.web.index.collectors;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TimingExtension implements BeforeEachCallback, AfterEachCallback {

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		System.out.println(">>> AFTER EACH");
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		System.out.println(">>> BEFORE EACH");
	}
}
