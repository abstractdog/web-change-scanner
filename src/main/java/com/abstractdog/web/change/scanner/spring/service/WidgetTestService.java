package com.abstractdog.web.change.scanner.spring.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abstractdog.web.change.scanner.Runner;
import com.abstractdog.web.change.scanner.spring.Utils;

@Service
public class WidgetTestService {
  private static Logger LOG = LoggerFactory.getLogger(WidgetTestService.class);
  private static final String LAST_WIDGET_TEST_RESULT_PATH = "work/last_widget_test_result.ser";

  static {
    new File(LAST_WIDGET_TEST_RESULT_PATH).getParentFile().mkdirs();
  }

  @Autowired
  ResultRegressionFilter resultRegressionFilter;

  @Autowired
  ResultReporter resultReporter;

  @PostConstruct
  public void init() {
    WidgetTestResult result = null;
    try {
      result = (WidgetTestResult) Utils.deSerializeObject(LAST_WIDGET_TEST_RESULT_PATH);
    } catch (Exception e) {
      LOG.warn("cannot deserialize widget test result from {}",
          new File(LAST_WIDGET_TEST_RESULT_PATH).getAbsolutePath(), e.getMessage());
    }
    resultRegressionFilter.setLastResult(result);
  }

  public void runWidgetAutoTestAndReport() throws Exception {
    LOG.info("running runWidgetAutoTestAndReport");
    Runner runner = Runner.getRunner();

    while (!Runner.running.compareAndSet(false, true)) {
      LOG.trace("selenium test Runner is already running, waiting a bit...");
      Thread.sleep(1000);
    }

    WidgetTestResult result;
    try {
      result = new WidgetTestResult(runner.run()).setCreatedDateNow();
    } finally {
      Runner.running.set(false);
    }
    if (result != null) {
      WidgetTestResult filtered = resultRegressionFilter.filter(result);
      LOG.info("found {} regression(s) of {} failures, reporting if >0 ...", filtered.getList().size(),
          result.getWarningsCount() + result.getFailuresCount());
      if (filtered.getList().size() > 0) {
        resultReporter.report(filtered);
      }
      persistResult(result);
    }
  }

  private void persistResult(WidgetTestResult result) {
    try {
      Utils.serializeObject(LAST_WIDGET_TEST_RESULT_PATH, result);
    } catch (Exception e) {
      LOG.warn("cannot serialize widget test result to {}", new File(LAST_WIDGET_TEST_RESULT_PATH).getAbsolutePath(),
          e);
    }
  }

  public void runEmptyReport() {
    resultReporter.report(new WidgetTestResult());
  }

  public WidgetTestResult getLastResult() {
    return resultRegressionFilter.getLastResult() == null ? null : resultRegressionFilter.getLastResult();
  }
}
