package com.abstractdog.web.change.scanner;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.abstractdog.web.change.scanner.webdriver.WebDriverUtils;

public class TestAjanlatadoSelenium {
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
	public void testAutoHitel() throws Exception {
		driver.get(
				"http://www.autohitel.hu/autohitel-kalkulator-2/?pi_uid=juh92tnu6fpi63jit8nu6fpi&onero=2000000&futamido=36&devizanem=HUF&ugyfelJelleg=T&casco=true&finanszirozasTipus=Z&gyartasiEv=2018&bruttoVetelar=3899000&eszkozJelleg=AS&korTipus=H&partnerId=13225&pi_track=9725FA1B2D1FEAA7675F185D1448E827&maradvanyertek=0&kamatozasTipus=V");

		List<WebElement> elements = driver
				.findElements(By.xpath("//div[contains(@class,'_ah_torleszto')]//*[contains(@class,'_ah_ertek')]"));
		Assert.assertEquals("60589", elements.get(0).getText().replaceAll("\\D+", ""));

		elements = driver.findElements(By.xpath("//div[contains(@class,'_ah_torleszto_osszesen')]"));
		Assert.assertEquals("2181204", elements.get(0).getText().replaceAll("\\D+", ""));
	}

	@Test
	public void testEszkozLizing() throws Exception {
		driver.get(
				"http://www.eszkozlizing.hu/widget.php?onero=12000000&futamido=60&devizanem=HUF&ugyletDeviza=HUF&maradvanyertek=0&ugyfelJelleg=T&casco=true&finanszirozasTipus=Z&akcioKod=WEBESK&gyartasiEv=2019&bruttoVetelar=60000000&eszkozJelleg=AN&korTipus=U&partnerId=15973&nhp=N");

		List<WebElement> elements = driver.findElements(By.xpath("//span[contains(@class,'_ahk_torleszto')]"));
		Assert.assertEquals("873203", elements.get(0).getText().replaceAll("\\D+", ""));

		elements = driver.findElements(By.xpath("//span[contains(@class,'_ah_torleszto_osszesen')]"));
		Assert.assertEquals("52392180", elements.get(0).getText().replaceAll("\\D+", ""));
	}
}
