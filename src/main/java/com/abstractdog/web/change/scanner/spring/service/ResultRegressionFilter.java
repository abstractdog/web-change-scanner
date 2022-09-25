package com.abstractdog.web.change.scanner.spring.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abstractdog.web.change.scanner.Result;

@Component
@Scope("singleton")
public class ResultRegressionFilter {

  private WidgetTestResult lastResult = null;

  public WidgetTestResult filter(WidgetTestResult result) {
    WidgetTestResult filtered =
        findTestResultRegressionsBetween(lastResult == null ? new WidgetTestResult() : lastResult, result);
    this.lastResult = new WidgetTestResult(result);

    return filtered;
  }

  public WidgetTestResult findTestResultRegressionsBetween(final WidgetTestResult resultBefore,
      WidgetTestResult resultNow) {
    WidgetTestResult filtered = resultNow.clone();

    filtered.getList().removeIf(result -> {
      Result oldResult = resultBefore.find(result);
      if (oldResult == null) {
        return result.isSuccessful(); // no reference result, no regression, only if current is failing
      }
      return result.isBetterOrSameAs(oldResult); // result is better than reference, no regression
    });

    return filtered;
  }

  public void setLastResult(WidgetTestResult result) {
    lastResult = result;
  }

  public WidgetTestResult getLastResult() {
    return lastResult;
  }

}
