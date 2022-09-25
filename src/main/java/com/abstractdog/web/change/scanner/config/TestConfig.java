package com.abstractdog.web.change.scanner.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.abstractdog.web.change.scanner.check.ValueChecker;
import com.abstractdog.web.change.scanner.check.ValueCheckerFactory;

public class TestConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  private Map<String, Object> config = new HashMap<>();

  public TestConfig(String url, String parameter, String value, String checker) {
    config.put("url", url);
    config.put("parameter", parameter);
    config.put("value", value);
    config.put("checker", checker);
  }

  public TestConfig(Map<String, Object> testConfig) {
    addStringPropertyFromMap("url", testConfig);
    addStringPropertyFromMap("parameter", testConfig);
    addStringPropertyFromMap("value", testConfig);

    config.put("checker", ValueCheckerFactory.getChecker((String) testConfig.get("checker")));
  }

  private void addStringPropertyFromMap(String key, Map<String, Object> testConfig) {
    config.put(key, testConfig.get(key));
  }

  public String getUrl() {
    return config.get("url").toString();
  }

  public String getParameter() {
    return config.get("parameter").toString();
  }

  public String getValue() {
    return config.get("value").toString();
  }

  public ValueChecker getChecker() {
    return (ValueChecker) config.get("checker");
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((config.get("url") == null) ? 0 : config.get("url").hashCode());
    result = prime * result + ((config.get("parameter") == null) ? 0 : config.get("parameter").hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TestConfig other = (TestConfig) obj;

    if (!config.get("url").equals(other.config.get("url"))) {
      return false;
    }
    if (!config.get("parameter").equals(other.config.get("parameter"))) {
      return false;
    }
    return true;
  }
}
