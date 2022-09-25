package com.abstractdog.web.change.scanner.check;

import com.abstractdog.web.change.scanner.Result.ResultStatus;
import com.abstractdog.web.change.scanner.parse.NumberParser;

public class NumberEqualityWithThresholdChecker implements ValueChecker {
  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_PERCENTAGE_THRESHOLD = 2;

  @Override
  public ResultStatus check(String value, String expectedValue) {
    double dblValue = NumberParser.INSTANCE.parse(value);
    double dblExpected = NumberParser.INSTANCE.parse(expectedValue);

    return isZero(dblValue) ? (isZero(dblExpected) ? ResultStatus.SUCCESS : ResultStatus.WARNING)
      : Math.abs(dblExpected - dblValue) / dblValue * 100.0 < DEFAULT_PERCENTAGE_THRESHOLD ? ResultStatus.SUCCESS
        : ResultStatus.WARNING;
  }

  private boolean isZero(double number) {
    return (Math.abs(number) < 2 * Double.MIN_VALUE);
  }
}
