package com.abstractdog.web.change.scanner.check;

import com.abstractdog.web.change.scanner.Result.ResultStatus;

public class BaseEqualCheck implements ValueChecker {
  private static final long serialVersionUID = 1L;

  @Override
  public ResultStatus check(String value, String expectedValue) {
    return value.equals(expectedValue) ? ResultStatus.SUCCESS : ResultStatus.WARNING;
  }

}
