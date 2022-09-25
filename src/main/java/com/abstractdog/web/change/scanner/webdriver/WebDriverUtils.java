package com.abstractdog.web.change.scanner.webdriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverUtils {
  private static Logger LOG = LoggerFactory.getLogger(WebDriverUtils.class);

  private static final String DEFAULT_CHROME_DRIVER_LOCATION = "src/main/resources/chromedriver";

  public static WebDriver getNewDriver() {
    return getChromeDriver();
  }

  private static WebDriver getChromeDriver() {
    ChromeOptions chromeOptions = new ChromeOptions();

    //FIXME: doesn't work with 'webdriver.chrome.headless'
    boolean headless = getProperty("webdriver.chrome.headless") == null || // default true
        !getProperty("webdriver.chrome.headless").equalsIgnoreCase("false");
    chromeOptions.setHeadless(headless);

    // default false
    boolean verbose = getProperty("webdriver.chrome.verbose") != null
        && getProperty("webdriver.chrome.verbose").equalsIgnoreCase("true");
    if (verbose) {
      chromeOptions.addArguments("--enable-logging=stderr");
      chromeOptions.addArguments(" --v=2");
    }

    chromeOptions.addArguments("--no-sandbox");
    chromeOptions.addArguments("--disable-dev-shm-usage");

    System.setProperty("webdriver.chrome.driver", getProperty("webdriver.chrome.driver") == null
      ? tryToFindChromeDriver() : getProperty("webdriver.chrome.driver"));

    LOG.info("creating chrome driver with options: {}", chromeOptions.asMap());
    return new ChromeDriver(chromeOptions);
  }

  private static String getProperty(String key) {
    return System.getProperty(key) != null ? System.getProperty(key) : System.getenv(key);
  }

  private static String tryToFindChromeDriver() {
    URL res = WebDriverUtils.class.getClassLoader().getResource("chromedriver");
    return res != null ? res.getPath() : DEFAULT_CHROME_DRIVER_LOCATION;
  }

  public static void waitUntilElementHasText(WebDriver driver, WebElement element) {
    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return element.getText().length() != 0;
      }
    });
  }

  public static WebElement waitUntilElementHasTextMultipleAttempts(WebDriver driver, String xpath, int attempts) {
    return waitUntilElementHasTextMultipleAttempts(driver, xpath, attempts, null);
  }

  public static WebElement waitUntilElementHasTextMultipleAttempts(WebDriver driver, String xpath, int attempts,
      WebDriverAction action) {
    for (int i = 0; i < attempts; i++) {
      try {
        LOG.debug(String.format("waiting for element %s, attempt: %d/%d", xpath, i + 1, attempts));

        if (action != null) {
          action.call(driver);
        }

        WebElement element =
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
            return element.getText().length() != 0;
          }
        });
        return element;
      } catch (TimeoutException | StaleElementReferenceException e) {
        driver.navigate().refresh();
      }
    }
    return null;
  }

  public static void takeScreenshot(WebDriver driver, File targetFile) {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      Files.copy(scrFile.toPath(), targetFile.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
