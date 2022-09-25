package com.abstractdog.web.change.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.abstractdog.web.change.scanner.Result;
import com.abstractdog.web.change.scanner.Result.ResultStatus;
import com.abstractdog.web.change.scanner.config.TestConfig;
import com.abstractdog.web.change.scanner.config.WidgetTest;
import com.abstractdog.web.change.scanner.spring.Utils;
import com.abstractdog.web.change.scanner.spring.service.WidgetTestResult;

public class TestUtils {

	@Test
	public void testWidgetTestResultSerialization() throws Exception {
		List<Result> tests = new ArrayList<>();
		WidgetTestResult result = new WidgetTestResult(tests);

		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.WARNING));
		Utils.serializeObject("widget_test_result.ser", result);
		Assert.assertTrue(new File("widget_test_result.ser").exists());
	}
}
