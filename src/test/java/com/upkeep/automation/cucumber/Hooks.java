package com.upkeep.automation.cucumber;

import com.google.common.collect.ImmutableMap;

import com.upkeep.automation.config.TestConfig;
import com.upkeep.automation.components.TestData;

import com.upkeep.automation.config.PropertyKeys;
import com.upkeep.automation.services.helpers.JQueryService;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@ContextConfiguration(classes = TestConfig.class)
public class Hooks extends RunCucumberTest {

  private String jobName;
  private String sessionId;
  private JQueryService jQueryService;

  @Autowired
  private Environment environment;

  @Autowired
  private WebDriver webDriver;

  @Autowired
  private TestData testData;

  public Hooks(JQueryService jQueryService) {
    this.jQueryService = jQueryService;
  }

  @Before
  public void init(Scenario scenario) throws Exception {
    String env = environment.getProperty(PropertyKeys.ENVIRONMENT.getLiteral());
    String profile = environment.getProperty(PropertyKeys.PROFILES.getLiteral());
    String driverVersion = ((RemoteWebDriver)
        webDriver).getCapabilities().getVersion();
  
    allureEnvironmentWriter(
        ImmutableMap.<String, String>builder()
            .put("Environment", env.toUpperCase())
            .put("Tags", scenario.getSourceTagNames().toString())
            .put("Browser", profile.toUpperCase())
            .put("Browser Version", driverVersion)
            .build());
    
    webDriver.manage().deleteAllCookies();
    testData.clear();
    jobName = scenario.getName();

    sessionId = (((RemoteWebDriver) webDriver).getSessionId()).toString();
  }

  @After
  public void embedDataIfFailed(Scenario scenario) {
    if (scenario.isFailed()) {
      try {
        Allure.addAttachment("Current page URL is: ",webDriver.getCurrentUrl());
        if (webDriver instanceof TakesScreenshot) {
          byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
          Allure.getLifecycle().addAttachment(scenario.getName(), "image/png", "png", screenshot);
        }
      
      } catch (Throwable somePlatformsDontSupportScreenshotsOrBrowserHasDied) {
        somePlatformsDontSupportScreenshotsOrBrowserHasDied.printStackTrace(System.err);
      }
    }
    jQueryService.executeJavascript("localStorage.clear()");
  }
}
