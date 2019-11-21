package com.upkeep.automation.config;

import com.upkeep.automation.components.TestProperties;
import com.upkeep.automation.services.helpers.AutomationWait;


import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WaitConfiguration {
  
  @Autowired
  private Environment environment;
  
  @Bean(name = "defaultWait")
  public AutomationWait defaultWait(TestProperties properties, WebDriver webDriver) {
    long waitTime = Long.parseLong(properties.getProperty(PropertyKeys.DEFAULT_AUTOMATION_WAIT.getLiteral()));
    log.debug("Default wait set to {}", waitTime + " seconds");
    return new AutomationWait(waitTime, webDriver);
  }
  
  @Bean(name = "maxWait")
  public AutomationWait maxWait(TestProperties properties, WebDriver webDriver) {
    long waitTime = Long.parseLong(properties.getProperty(PropertyKeys.MAX_AUTOMATION_WAIT.getLiteral()));
    return new AutomationWait(waitTime, webDriver);
  }
  
  @Bean(name = "animationWait")
  public AutomationWait animationWait(TestProperties properties, WebDriver webDriver) {
    long waitTime = Long.parseLong(properties.getProperty(PropertyKeys.ANIMATION_WAIT.getLiteral()));
    return new AutomationWait(waitTime, webDriver);
  }
  
  @Bean(name = "reactionWait")
  public AutomationWait reactionWait(TestProperties properties, WebDriver webDriver) {
    long waitTime = Long.parseLong(properties.getProperty(PropertyKeys.REACTION_WAIT.getLiteral()));
    return new AutomationWait(waitTime, webDriver);
  }
  
}
