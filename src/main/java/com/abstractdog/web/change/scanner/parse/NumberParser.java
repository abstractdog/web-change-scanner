package com.abstractdog.web.change.scanner.parse;

public class NumberParser implements Parser {
  public static final NumberParser INSTANCE = new NumberParser();

  private NumberParser() {
  }

  @Override
  public Double parse(String value) {
    String strNumber = value.replaceAll(",", ".").replaceAll("[^0-9]+", "");
    return Double.parseDouble(strNumber);
  }
}
