package com.upkeep.automation.components;

import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppContextListener {
  
  private final WebDriver webDriver;
  private final TestProperties properties;
  
  @Autowired
  public AppContextListener(WebDriver webDriver, TestProperties properties) {
    this.webDriver = webDriver;
    this.properties = properties;
  }
  
  @PreDestroy
  public void destroy() {
    String driverType = properties.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    if (driverType != null) {
      webDriver.quit();
    }
  }
}

