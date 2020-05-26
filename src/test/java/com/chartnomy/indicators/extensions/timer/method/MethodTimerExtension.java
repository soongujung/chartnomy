package com.chartnomy.indicators.extensions.timer.method;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class MethodTimerExtension implements BeforeTestExecutionCallback,
	AfterTestExecutionCallback {

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		Store store = getStore(context);
		store.put("START_TIME", System.currentTimeMillis());
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		Store store = getStore(context);

		Long start_time = store.remove("START_TIME",Long.class);
		Long duration = System.currentTimeMillis() - start_time;

		System.out.println("test took " + duration + "ms");
	}

	private Store getStore(ExtensionContext context) {
		String testClassName = context.getRequiredTestClass().getName();
		String testMethodName = context.getRequiredTestMethod().getName();
		Namespace namespace = Namespace.create(testClassName, testMethodName);
		Store store = context.getStore(namespace);
		return store;
	}
}
