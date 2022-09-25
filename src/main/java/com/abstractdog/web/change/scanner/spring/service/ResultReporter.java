package com.abstractdog.web.change.scanner.spring.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ResultReporter {
  private static Logger LOG = LoggerFactory.getLogger(ResultReporter.class);

  @Value("#{'${widget.test.report.recipients:}'.split(',')}")
  private List<String> recipients;

  @Value("${widget.test.report.from}")
  private String from;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  TemplateEngine templateEngine;

  @PostConstruct
  public void init() {
    LOG.info("initialized result reporter with recipients: {}", recipients);
  }

  public void report(WidgetTestResult result) {
    MimeMessage msg = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");

    try {
      helper.setTo(recipients.toArray(new String[0]));
      helper.setFrom(from);
      helper.setSubject(String.format("Widget test report [%d new warnings / %d new failures] [%s]",
          result.getWarningsCount(), result.getFailuresCount(),
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
      helper.setText(getReportText(result), true);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

    mailSender.send(msg);
  }

  private String getReportText(WidgetTestResult result) {
    return templateEngine.process("widget_test_report", new Context(Locale.getDefault(), result.getMap()));
  }
}
