package com.abstractdog.web.change.scanner.thymeleaf;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.abstractdog.web.change.scanner.Result;
import com.abstractdog.web.change.scanner.Result.ResultStatus;
import com.abstractdog.web.change.scanner.config.TestConfig;
import com.abstractdog.web.change.scanner.config.WidgetTest;
import com.abstractdog.web.change.scanner.spring.service.WidgetTestResult;

@Configuration
public class TestThymeleafWidgetReport {
  public TemplateEngine templateEngine() {
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    return templateEngine;
  }

  private TemplateResolver templateResolver() {
    TemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix("src/main/resources/templates/");
    resolver.setSuffix(".html");
    resolver.setCacheable(true);
    return resolver;
  }

  @Test
  public void testWidgetReport() throws Exception {
    Map<String, Object> context = getDummyResult().getMap();

    String result = templateEngine().process("widget_test_report", new Context(Locale.getDefault(), context));
    System.out.println(result);
    Files.write(Paths.get(String.format("target/widget_test_report_result.html")), result.getBytes());
  }

  private WidgetTestResult getDummyResult() {
    List<Result> tests = new ArrayList<>();
    WidgetTestResult result = new WidgetTestResult(tests);

    result.setCreatedDateNow();
    tests.add(
        new Result(new WidgetTest(new TestConfig("http://dummywidget1.com", "torlesztoreszlet", "21393", null), null),
            "48345", ResultStatus.WARNING));
    tests.add(
        new Result(new WidgetTest(new TestConfig("http://dummywidget2.com", "osszes torleszto", "333444", null), null),
            "333444", ResultStatus.SUCCESS));
    tests.add(
        new Result(new WidgetTest(new TestConfig("http://dummywidget3.com", "osszes torleszto", "12345", null), null),
            "-1", ResultStatus.FAILURE));
    return result;
  }
}
