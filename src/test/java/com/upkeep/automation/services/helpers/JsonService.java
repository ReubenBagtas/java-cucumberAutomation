package com.upkeep.automation.services.helpers;

import com.jayway.restassured.response.Response;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JsonService {

  public Object getJson(Response response, String path) {
    return response.jsonPath().get(normalizePath(path));
  }

  public String getJsonString(Response response, String path) {
    return response.jsonPath().getString(normalizePath(path));
  }

  public int getJsonInt(Response response, String path) {
    return response.jsonPath().getInt(normalizePath(path));
  }

  public boolean getJsonBoolean(Response response, String path) {
    return response.jsonPath().getBoolean(normalizePath(path));
  }

  public BigDecimal getJsonBigDecimal(Response response, String path) {
    Object o = getJsonObject(response, path);
    if (o instanceof Integer) {
      Integer i = (Integer)o;
      return BigDecimal.valueOf(i.longValue());
    } else {
      return o instanceof Long ? BigDecimal.valueOf(((Long)o).longValue()) : (BigDecimal)o;
    }
  }

  public <T> List<T> getJsonList(Response response, String path) {
    return response.jsonPath().getList(normalizePath(path));
  }

  public <T> T getJsonObject(Response response, String path) {
    return response.jsonPath().getJsonObject(normalizePath(path));
  }

  private String normalizePath(String path) {
    return path.replaceAll("\\$\\.", "restAssuredJsonRootObject.");
  }

  public boolean isNumber(String str) {
    try {
      float v = Float.parseFloat(str);
      return true;
    } catch (NumberFormatException nfe) {
      System.out.print(nfe);
    }
    return false;
  }
}
