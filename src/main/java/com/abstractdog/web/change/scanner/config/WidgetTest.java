package com.abstractdog.web.change.scanner.config;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.abstractdog.web.change.scanner.check.ValueChecker;

public class WidgetTest implements Serializable {
  private static final long serialVersionUID = 1L;

  private TestConfig config;
  private String xpath;

  public WidgetTest(TestConfig testConfig, Map<String, Object> xpathsConfig) {
    this.config = testConfig;
    this.xpath = parseXPath(testConfig, xpathsConfig);
  }

  @SuppressWarnings("unchecked")
  private String parseXPath(TestConfig testConfig, Map<String, Object> xpathsConfig) {
    String domain = getDomain();
    String parameter = config.getParameter();

    if (xpathsConfig == null) {
      return null;
    }
    if (!xpathsConfig.containsKey(parameter)) {
      throw new IllegalArgumentException("cannot find xpath mapping for parameter: " + parameter);
    }

    Map<String, Object> parameterXPathConfig = (Map<String, Object>) xpathsConfig.get(parameter);
    if (!parameterXPathConfig.containsKey("default") && !parameterXPathConfig.containsKey(domain)) {
      throw new IllegalArgumentException(
          "cannot find default or domain specific xpath mapping for parameter: " + parameter);
    }

    return parameterXPathConfig.get(domain) != null ? parameterXPathConfig.get(domain).toString()
      : parameterXPathConfig.get("default").toString();
  }

  public String getUrl() {
    return config.getUrl();
  }

  public String getDomain() {
    try {
      return new URL(getUrl()).getHost().replaceAll("www.", "");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public String getXPath() {
    return xpath;
  }

  public String getValue() {
    return config.getValue();
  }

  public ValueChecker getChecker() {
    return config.getChecker();
  }

  public String toString() {
    return String.format("Test - url: %s, xpath: %s, value: %s", getUrl(), xpath, config.getValue());
  }

  public Map<String, Object> getMap() {
    Map<String, Object> map = new HashMap<String, Object>();

    map.put("url", config.getUrl());
    map.put("parameter", config.getParameter());
    map.put("value", config.getValue());
    map.put("xpath", xpath);

    return map;
  }

  @Override
  public int hashCode() {
    return config.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    WidgetTest widgetTestOther = (WidgetTest) other;
    return widgetTestOther.config.equals(config);
  }
}
