package com.abstractdog.web.change.scanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.abstractdog.web.change.scanner.check.ValueChecker;
import com.abstractdog.web.change.scanner.check.test.GoogleSpreadsheetTestSource;
import com.abstractdog.web.change.scanner.config.Config;
import com.abstractdog.web.change.scanner.config.WidgetTest;
import com.abstractdog.web.change.scanner.webdriver.IFrameSwitcher;
import com.abstractdog.web.change.scanner.webdriver.WebDriverUtils;

public class Runner {
  public static AtomicBoolean running = new AtomicBoolean(false);
  private static Logger LOG = LoggerFactory.getLogger(Runner.class);
  private static Runner instance;
  private Config config;

  public static void main(String[] args) throws Exception {
    BasicConfigurator.configure();

    Runner.instance = getRunner();

    Runner.instance.run();
  }

  public static Runner getRunner() throws IOException {
    Yaml yaml = new Yaml();
    try (InputStream configStream = Runner.class.getClassLoader().getResourceAsStream("config.yaml")) {
      Map<String, Object> config = yaml.load(configStream);

      // config.put("tests", new YamlTestSource("tests.yaml").getTests());
      config.put("tests", new GoogleSpreadsheetTestSource(System.getProperty("google.api.key"),
          "1WwA0tDTbkIjXXCpc02BC11mCC8H8YSC3wzf7fOA4Muc", "urls").getTests());

      return new Runner(new Config(config));
    }
  }

  public Runner(Config config) {
    this.config = config;
  }

  public List<Result> run() throws Exception {
    WebDriver driver = WebDriverUtils.getNewDriver();

    int index = 0;
    int successCount = 0;

    List<Result> failedTestResults = new ArrayList<>();
    List<Result> allResults = new ArrayList<>();

    for (WidgetTest test : config.getTests()) {
      LOG.info("RUNNING TEST: {}/{} {}", index + 1, config.getTests().size(), test.getUrl());

      Result result = runTest(test, driver);
      allResults.add(result);

      if (result.isSuccessful()) {
        successCount++;
      } else {
        failedTestResults.add(result);
      }

      LOG.info("current statistics, {} out of {} tests succeeded", successCount, index + 1);
      index++;
    }

    LOG.info("failed tests: {}", failedTestResults);
    driver.quit();

    return allResults;
  }

  private Result runTest(WidgetTest test, WebDriver driver) throws Exception {
    String url = test.getUrl();
    url = config.appendDefaultParametersToUrl(url);

    LOG.debug("request url: {}", url);
    driver.get(url);

    Result result = check(driver, test);

    LOG.info(result.toString());
    if (!result.isSuccessful()) {
      File screenshot = new File(System.getProperty("java.io.tmpdir"),
          String.format("aja_selenium_test_%d.png", System.currentTimeMillis()));
      WebDriverUtils.takeScreenshot(driver, screenshot);

      LOG.info("screenshot created at: {}", screenshot.getCanonicalPath());
    }

    return result;
  }

  private Result check(WebDriver driver, WidgetTest test) {
    String iFrameSelector = config.getIFrameForDomain(test.getDomain());

    WebElement webElement = WebDriverUtils.waitUntilElementHasTextMultipleAttempts(driver, test.getXPath(), 3,
        iFrameSelector == null ? null : new IFrameSwitcher(By.tagName("iframe").cssSelector(iFrameSelector)));

    if (webElement == null) {
      File screenshot = new File(System.getProperty("java.io.tmpdir"),
          String.format("aja_selenium_test_%d.png", System.currentTimeMillis()));
      WebDriverUtils.takeScreenshot(driver, screenshot);

      return new Result(test, "-1", Result.ResultStatus.FAILURE);
    }

    ValueChecker checker = test.getChecker();
    String value = webElement.getText();
    return new Result(test, value, checker.check(value, test.getValue()));
  }
}
