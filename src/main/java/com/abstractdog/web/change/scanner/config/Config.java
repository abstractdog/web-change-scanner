package com.abstractdog.web.change.scanner.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
  private Map<String, Object> defaultParameters;
  private Map<String, Object> iframes;
  private List<WidgetTest> tests;

  public Config(Map<String, Object> config) {
    parseConfig(config);
  }

  @SuppressWarnings("unchecked")
  private void parseConfig(Map<String, Object> config) {
    defaultParameters = config.get("default_parameters") == null ? new HashMap<>()
      : (Map<String, Object>) config.get("default_parameters");
    iframes = (Map<String, Object>) config.get("iframes");
    tests = new ArrayList<WidgetTest>();

    for (Map<String, Object> test : (List<Map<String, Object>>) config.get("tests")) {
      tests.add(new WidgetTest(new TestConfig(test), (Map<String, Object>) config.get("xpaths")));
    }
    iframes = (Map<String, Object>) config.get("iframes");
  }

  public List<WidgetTest> getTests() {
    return tests;
  }

  public String appendDefaultParametersToUrl(String url) throws Exception {
    URI oldUri = new URI(url);
    String appendQuery = defaultParameters.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue())
        .reduce((p1, p2) -> p1 + "&" + p2).orElse("").toString();

    String newQuery = oldUri.getQuery();
    if (newQuery == null) {
      newQuery = appendQuery;
    } else {
      newQuery += "&" + appendQuery;
    }

    URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(), oldUri.getPath(), newQuery, oldUri.getFragment());

    return newUri.toString();
  }

  public String getIFrameForDomain(String domain) {
    return iframes == null ? null : (String) iframes.get(domain);
  }
}
