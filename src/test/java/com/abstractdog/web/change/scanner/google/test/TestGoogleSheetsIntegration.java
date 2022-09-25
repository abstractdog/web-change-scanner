package com.abstractdog.web.change.scanner.google.test;

import java.util.List;

import org.junit.Test;

import com.abstractdog.web.change.scanner.google.GoogleSheetUtil;

public class TestGoogleSheetsIntegration {
	private static String SPREADSHEET_ID = "1EDDhiQV0VcAqJPmWX7cy8_G4D0nOVYFP8uBl9uwwBAU";
	private static String API_KEY = "AIzaSyDrWxu1RPa5vkA8dB7U3tBT5JoWfT5q5jE";
	private static String SHEET_NAME = "urls";

	@Test
	public void testRead() throws Exception {
		List<List<String>> items = GoogleSheetUtil.downloadJson(API_KEY, SPREADSHEET_ID, SHEET_NAME);
		System.out.println(items);
	}
}