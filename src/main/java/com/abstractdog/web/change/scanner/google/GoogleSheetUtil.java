package com.abstractdog.web.change.scanner.google;

import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleSheetUtil {
  private final static String BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets/";
  private final static String MAX_RANGE = "A1:Z9999999";
  private final static String ACTION_VALUES = "values";

  @SuppressWarnings("unchecked")
  public static List<List<String>> downloadJson(String apiKey, String docId, String sheetName) throws Exception {
    String url = generateUrl(docId, sheetName, apiKey);

    String content = Request.Get(url).execute().returnContent().toString();

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> data = (Map<String, Object>) mapper.readValue(content, Map.class);

    return (List<List<String>>) data.get(ACTION_VALUES);
  }

  private static String generateUrl(String docId, String sheetName, String apiKey) {
    return BASE_URL + docId + "/" + ACTION_VALUES + "/" + sheetName + "!" + MAX_RANGE + "?key=" + apiKey;
  }

}
