package com.abstractdog.web.change.scanner;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.abstractdog.web.change.scanner.webdriver.WebDriverUtils;

public class TestSelenium {

	private static WebDriver driver;
	
	@BeforeClass
	public static void setup() {
		driver = WebDriverUtils.getNewDriver();
	}
	
	@AfterClass
	public static void shutdown() {
		driver.quit();
	}
	
	@Test
	public void testFirst() {
		driver.get("https://google.com");
		Assert.assertTrue(driver.getTitle().toLowerCase().contains("google"));
	}
}
