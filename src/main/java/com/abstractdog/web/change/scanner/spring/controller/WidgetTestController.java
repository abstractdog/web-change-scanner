package com.abstractdog.web.change.scanner.spring.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abstractdog.web.change.scanner.spring.service.WidgetTestResult;
import com.abstractdog.web.change.scanner.spring.service.WidgetTestService;

@RestController
@RequestMapping(value = "/widget_test")
public class WidgetTestController {
  @Autowired
  WidgetTestService widgetTestService;

  @RequestMapping(value = "/lastResult", method = RequestMethod.GET)
  public Map<String, Object> lastResult() throws Exception {
    return resultToResponse(widgetTestService.getLastResult());
  }

  @RequestMapping(value = "/report", method = RequestMethod.GET)
  public void report() throws Exception {
    widgetTestService.runWidgetAutoTestAndReport();
  }

  @RequestMapping(value = "/reportEmpty", method = RequestMethod.GET)
  public void reportEmpty() throws Exception {
    widgetTestService.runEmptyReport();
  }

  private Map<String, Object> resultToResponse(WidgetTestResult result) {
    Map<String, Object> response = new HashMap<>();

    if (result == null) {
      response.put("success", false);
      response.put("message", "RESULT_NOT_FOUND");
    } else {
      List<Map<String, Object>> list = result.getList().stream().map(m -> m.getMap()).collect(Collectors.toList());
      response.put("success", true);
      response.put("count", list.size());
      response.put("count_successful", result.getSuccessCount());
      response.put("count_warning", result.getWarningsCount());
      response.put("count_failed", result.getFailuresCount());
      response.put("date", result.getDate());
      response.put("date_str", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(result.getDate()));
      response.put("result", list);
    }

    return response;
  }
}
