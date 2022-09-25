package com.abstractdog.web.change.scanner.parse.test;

import org.junit.Test;

import com.abstractdog.web.change.scanner.parse.NumberParser;

import org.junit.Assert;

public class TestNumberParser {

  @Test
  public void testBasicParse() {
    Assert.assertEquals(1234.0, NumberParser.INSTANCE.parse("1234").doubleValue(), 0.001);
    Assert.assertEquals(1234.0, NumberParser.INSTANCE.parse("1 234").doubleValue(), 0.001);
    Assert.assertEquals(1234034.0, NumberParser.INSTANCE.parse("1 234 034").doubleValue(), 0.001);
    Assert.assertEquals(0.0, NumberParser.INSTANCE.parse("0 Eur").doubleValue(), 0.001);
    Assert.assertEquals(21990.0, NumberParser.INSTANCE.parse("21.990 Ft").doubleValue(), 0.001);
    Assert.assertEquals(9793.0, NumberParser.INSTANCE.parse("9.793 Ft-tól").doubleValue(), 0.001);
  }
}
