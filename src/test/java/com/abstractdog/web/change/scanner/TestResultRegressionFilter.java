package com.abstractdog.web.change.scanner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.abstractdog.web.change.scanner.Result;
import com.abstractdog.web.change.scanner.Result.ResultStatus;
import com.abstractdog.web.change.scanner.config.TestConfig;
import com.abstractdog.web.change.scanner.config.WidgetTest;
import com.abstractdog.web.change.scanner.spring.service.ResultRegressionFilter;
import com.abstractdog.web.change.scanner.spring.service.WidgetTestResult;

import org.junit.Assert;

public class TestResultRegressionFilter {

	@Test
	public void testFilterEqualResults() {
		List<Result> tests = new ArrayList<>();
		WidgetTestResult result = new WidgetTestResult(tests);

		result.setCreatedDateNow();
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.WARNING));
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "osszes torleszto", "333444", null), null),
				"333444", ResultStatus.SUCCESS));
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget3.com", "osszes torleszto", "12345", null), null),
				"-1", ResultStatus.FAILURE));

		List<Result> tests2 = new ArrayList<>();
		WidgetTestResult result2 = new WidgetTestResult(tests2);

		result2.setCreatedDateNow();
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.WARNING));
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "osszes torleszto", "333444", null), null),
				"333444", ResultStatus.SUCCESS));
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget3.com", "osszes torleszto", "12345", null), null),
				"-1", ResultStatus.FAILURE));

		Assert.assertEquals(0,
				new ResultRegressionFilter().findTestResultRegressionsBetween(result, result2).getList().size());
	}

	@Test
	public void testFilterOneRegression() {
		List<Result> tests = new ArrayList<>();
		WidgetTestResult result = new WidgetTestResult(tests);

		result.setCreatedDateNow();
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "osszes torleszto", "333444", null), null),
				"333444", ResultStatus.SUCCESS));

		List<Result> tests2 = new ArrayList<>();
		WidgetTestResult result2 = new WidgetTestResult(tests2);

		result2.setCreatedDateNow();
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.WARNING));
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "osszes torleszto", "333444", null), null),
				"333444", ResultStatus.SUCCESS));

		Assert.assertEquals(1,
				new ResultRegressionFilter().findTestResultRegressionsBetween(result, result2).getList().size());
	}

	@Test
	public void testFilterNewWrongTestIntroduced() {
		List<Result> tests = new ArrayList<>();
		WidgetTestResult result = new WidgetTestResult(tests);

		result.setCreatedDateNow();
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));

		List<Result> tests2 = new ArrayList<>();
		WidgetTestResult result2 = new WidgetTestResult(tests2);

		result2.setCreatedDateNow();
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.FAILURE));

		Assert.assertEquals(1,
				new ResultRegressionFilter().findTestResultRegressionsBetween(result, result2).getList().size());
	}

	@Test
	public void testFilterNewGoodTestIntroduced() {
		List<Result> tests = new ArrayList<>();
		WidgetTestResult result = new WidgetTestResult(tests);

		result.setCreatedDateNow();
		tests.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));

		List<Result> tests2 = new ArrayList<>();
		WidgetTestResult result2 = new WidgetTestResult(tests2);

		result2.setCreatedDateNow();
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));
		tests2.add(new Result(
				new WidgetTest(new TestConfig("http://dummywidget2.com", "torlesztoreszlet", "21393", null), null),
				"48345", ResultStatus.SUCCESS));

		Assert.assertEquals(0,
				new ResultRegressionFilter().findTestResultRegressionsBetween(result, result2).getList().size());
	}
}
