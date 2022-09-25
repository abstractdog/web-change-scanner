package com.abstractdog.web.change.scanner.spring.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abstractdog.web.change.scanner.Result;

public class WidgetTestResult implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  private List<Result> results;
  private Date createdDate;

  public WidgetTestResult(List<Result> results) {
    this.results = results;
    this.createdDate = new Date();
  }

  public WidgetTestResult(WidgetTestResult result) {
    this.results = new ArrayList<>(result.results);
    this.createdDate = result.createdDate;
  }

  public WidgetTestResult() {
    this(new ArrayList<Result>());
  }

  public WidgetTestResult setCreatedDateNow() {
    this.createdDate = new Date();
    return this;
  }

  public List<Result> getList() {
    return results;
  }

  public Map<String, Object> getMap() {
    Map<String, Object> map = new HashMap<>();

    map.put("createdDate", createdDate);
    map.put("results", getList());

    return map;
  }

  public Result find(Result result) {
    return results.contains(result) ? results.get(results.indexOf(result)) : null;
  }

  @Override
  public WidgetTestResult clone() {
    WidgetTestResult clone = new WidgetTestResult(new ArrayList<>(results));
    clone.createdDate = new Date(this.createdDate.getTime());

    return clone;
  }

  public long getSuccessCount() {
    return getList().stream().filter(r -> r.isSuccessful()).count();
  }

  public long getFailuresCount() {
    return getList().stream().filter(r -> r.isFailure()).count();
  }

  public long getWarningsCount() {
    return getList().stream().filter(r -> r.isWarning()).count();
  }

  public Date getDate() {
    return createdDate;
  }
}
