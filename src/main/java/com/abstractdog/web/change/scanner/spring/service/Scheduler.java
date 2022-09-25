package com.abstractdog.web.change.scanner.spring.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author abstractdog Basic sheduler for the services. Can be refactored
 *         to multiple classes if needed later.
 */
@Component
public class Scheduler {
  private static Logger LOG = LoggerFactory.getLogger(Scheduler.class);

  @Autowired
  WidgetTestService widgetTestService;

  @PostConstruct
  public void init() {
    LOG.info("initialized scheduler");
  }

  @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 0)
  public void runWidgetAutoTestAndReport() throws Exception {
    widgetTestService.runWidgetAutoTestAndReport();
  }
}
