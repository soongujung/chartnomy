package com.chartnomy.indicators.temp.custom.params;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

public class CustomTestInvocationContext implements TestTemplateInvocationContext {

	@Override
	public String getDisplayName(int invocationIndex) {
		return "Argument :: " + String.valueOf(invocationIndex) + " at CustomTestInvocationContext::getDisplayName";
	}

	@Override
	public List<Extension> getAdditionalExtensions() {
		return new ArrayList<>();
	}
}
