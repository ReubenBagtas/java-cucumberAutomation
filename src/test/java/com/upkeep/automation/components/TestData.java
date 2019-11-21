package com.upkeep.automation.components;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestData {

  private Map<String, Object> data = new HashMap<>();
  
  public void put(String key, Object value) {
    this.data.put(key, value);
  }

  public Object get(String key) {
    return this.data.get(key);
  }
  
  public Object getOrDefault(String key, Object defaultValue) {
    return this.data.getOrDefault(key, defaultValue);
  }

  public void clear() {
    data.clear();
  }
}
