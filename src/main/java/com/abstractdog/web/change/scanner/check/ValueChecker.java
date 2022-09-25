package com.abstractdog.web.change.scanner.check;

import java.io.Serializable;

import com.abstractdog.web.change.scanner.Result.ResultStatus;

public interface ValueChecker extends Serializable {
  ResultStatus check(String value, String expectedValue);
}
