package com.abstractdog.web.change.scanner;

import org.junit.Test;

import com.abstractdog.web.change.scanner.Result.ResultStatus;

import org.junit.Assert;

public class TestResult {

	@Test
	public void testResultComparison() {
		Assert.assertTrue(ResultStatus.SUCCESS.isBetterOrSameAs(ResultStatus.SUCCESS));
		Assert.assertTrue(ResultStatus.SUCCESS.isBetterOrSameAs(ResultStatus.WARNING));
		Assert.assertTrue(ResultStatus.SUCCESS.isBetterOrSameAs(ResultStatus.FAILURE));
		Assert.assertTrue(ResultStatus.WARNING.isBetterOrSameAs(ResultStatus.WARNING));
		Assert.assertTrue(ResultStatus.WARNING.isBetterOrSameAs(ResultStatus.FAILURE));
		Assert.assertTrue(ResultStatus.FAILURE.isBetterOrSameAs(ResultStatus.FAILURE));

		Assert.assertFalse(ResultStatus.WARNING.isBetterOrSameAs(ResultStatus.SUCCESS));
		Assert.assertFalse(ResultStatus.FAILURE.isBetterOrSameAs(ResultStatus.SUCCESS));
		Assert.assertFalse(ResultStatus.FAILURE.isBetterOrSameAs(ResultStatus.WARNING));
	}
}
