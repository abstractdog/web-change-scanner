package com.abstractdog.web.change.scanner;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.abstractdog.web.change.scanner.check.test.YamlTestSource;
import com.abstractdog.web.change.scanner.config.Config;

public class Utils {

  public static Config getTestConfigFromYamlClassPathResources(String configYamlClassPathRes,
      String testsYamlClassPathRes) throws Exception {
    Map<String, Object> config = null;

    Yaml yaml = new Yaml();
    try (InputStream configStream = Runner.class.getClassLoader().getResourceAsStream(configYamlClassPathRes)) {
      config = yaml.load(configStream);
      config.put("tests", new YamlTestSource(testsYamlClassPathRes).getTests());
    }
    return new Config(config);
  }
}
