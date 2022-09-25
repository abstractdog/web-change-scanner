package com.abstractdog.web.change.scanner.check.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.abstractdog.web.change.scanner.Runner;

public class YamlTestSource implements TestSource {
  private String path;

  public YamlTestSource(String path) {
    this.path = path;
  }

  @Override
  public List<Map<String, Object>> getTests() {
    Yaml yaml = new Yaml();

    try (InputStream testStream = Runner.class.getClassLoader().getResourceAsStream(path)) {
      return yaml.load(testStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
