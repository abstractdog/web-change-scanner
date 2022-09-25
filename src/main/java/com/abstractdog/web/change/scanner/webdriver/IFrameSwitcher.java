package com.abstractdog.web.change.scanner.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IFrameSwitcher implements WebDriverAction {
  private static Logger LOG = LoggerFactory.getLogger(IFrameSwitcher.class);

  private By locator;

  public IFrameSwitcher(By locator) {
    this.locator = locator;
  }

  @Override
  public void call(WebDriver driver) {
    WebElement iframe = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));

    LOG.debug(String.format("switching to iframe %s", iframe.getAttribute("src")));
    driver.switchTo().frame(iframe);
  }

}
