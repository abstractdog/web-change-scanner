package com.abstractdog.web.change.scanner.check;

public class ValueCheckerFactory {

  public static ValueChecker getChecker(String name) {
    if (name == null) {
      return new NumberEqualityWithThresholdChecker();
    }
    throw new RuntimeException("not yet implemented");
  }

}
