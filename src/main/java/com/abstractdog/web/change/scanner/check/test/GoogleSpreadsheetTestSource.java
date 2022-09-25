package com.abstractdog.web.change.scanner.check.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.abstractdog.web.change.scanner.google.GoogleSheetUtil;

public class GoogleSpreadsheetTestSource implements TestSource {
  private String apiKey;
  private String spreadsheetId;
  private String sheetName;

  public GoogleSpreadsheetTestSource(String apiKey, String spreadSheetId, String sheetName) {
    this.apiKey = apiKey;
    this.spreadsheetId = spreadSheetId;
    this.sheetName = sheetName;
  }

  @Override
  public List<Map<String, Object>> getTests() {
    List<List<String>> data;
    try {
      data = GoogleSheetUtil.downloadJson(apiKey, spreadsheetId, sheetName);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    data.remove(0); // header

    return data.stream().map(entry -> {
      Map<String, Object> item = new HashMap<String, Object>();
      item.put("url", entry.get(0));
      item.put("parameter", entry.get(1));
      item.put("value", entry.get(2));

      return item;
    }).collect(Collectors.toList());
  }

}
