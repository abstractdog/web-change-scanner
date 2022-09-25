package com.abstractdog.web.change.scanner.pages;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.abstractdog.web.change.scanner.Result;
import com.abstractdog.web.change.scanner.Runner;
import com.abstractdog.web.change.scanner.Utils;
import com.abstractdog.web.change.scanner.config.Config;

import org.junit.Assert;
import org.junit.BeforeClass;

public class TestPages {

  @BeforeClass
  public static void beforeClass() {
    BasicConfigurator.configure();
    if (System.getProperty("webdriver.chrome.driver") == null) {
      System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }
  }

  @Test
  public void testAboutYou() throws Exception {
    Config config = Utils.getTestConfigFromYamlClassPathResources("config.yaml", "aboutyou.yaml");
    doTest(config);
  }

  private void doTest(Config config) throws Exception {
    Runner runner = new Runner(config);
    List<Result> result = runner.run();
    Assert.assertEquals(0, result.stream().filter(r -> !r.isSuccessful()).count());
  }
}
