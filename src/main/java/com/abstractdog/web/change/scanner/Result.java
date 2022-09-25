package com.abstractdog.web.change.scanner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.abstractdog.web.change.scanner.config.WidgetTest;

public class Result implements Serializable {
  private static final long serialVersionUID = 1L;

  public enum ResultStatus {
    SUCCESS(true, 1), WARNING(false, 2), FAILURE(false, 3);

    private final boolean successful;
    private final int successLevel;

    private ResultStatus(final boolean successful, final int successLevel) {
      this.successful = successful;
      this.successLevel = successLevel;
    }

    public boolean isSuccessful() {
      return this.successful;
    }

    public boolean isBetterOrSameAs(ResultStatus other) {
      return this.successLevel <= other.successLevel;
    }
  }

  private WidgetTest test;
  private String result;
  private ResultStatus status;

  public Result(WidgetTest test, String result, ResultStatus status) {
    this.test = test;
    this.result = result;
    this.status = status;
  }

  public boolean isSuccessful() {
    return this.status.isSuccessful();
  }

  public boolean isFailure() {
    return getStatus().equals(ResultStatus.FAILURE);
  }

  public boolean isWarning() {
    return getStatus().equals(ResultStatus.WARNING);
  }

  public ResultStatus getStatus() {
    return this.status;
  }

  public String toString() {
    return String.format("[Result] %s, %s, expected:%s <-> actual:%s", status, test.getUrl(), test.getValue(), result);
  }

  public Map<String, Object> getMap() {
    Map<String, Object> map = new HashMap<String, Object>();

    map.put("result", result);
    map.put("status", status);
    map.put("test", test.getMap());

    return map;
  }

  @Override
  public int hashCode() {
    return test.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    Result resultOther = (Result) other;
    return resultOther.test.equals(test);
  }

  public boolean isBetterOrSameAs(Result other) {
    return status.isBetterOrSameAs(other.status);
  }
}
