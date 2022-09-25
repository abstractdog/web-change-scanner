package com.abstractdog.web.change.scanner.thymeleaf;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.junit.Assert;

@Configuration
public class TestThymeleaf {
  public TemplateEngine templateEngine() {
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    return templateEngine;
  }

  private TemplateResolver templateResolver() {
    TemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix("src/test/resources/templates/");
    resolver.setSuffix(".html");
    resolver.setCacheable(true);
    return resolver;
  }

  @Test
  public void testBase() {
    Map<String, Object> context = new HashMap<>();
    context.put("name", "user");

    String result = templateEngine().process("thymeleaf1", new Context(Locale.getDefault(), context));
    Assert.assertTrue(result.contains("<p>Hello, user!</p>"));
  }
}
