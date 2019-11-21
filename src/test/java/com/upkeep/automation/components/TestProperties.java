package com.upkeep.automation.components;

import com.upkeep.automation.config.PropertyKeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestProperties {

  private final Environment environment;

  @Autowired
  public TestProperties(Environment environment) {
    this.environment = environment;
  }

  public String getProperty(String propertyName) {
    return environment.getProperty(propertyName);
  }

  public String getBaseUrl() {
    return environment.getProperty(PropertyKeys.BASE_URL.getLiteral());
  }
}
