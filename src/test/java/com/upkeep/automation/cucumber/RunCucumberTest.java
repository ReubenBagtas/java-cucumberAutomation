package com.upkeep.automation.cucumber;

import org.springframework.test.context.TestExecutionListeners;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@TestExecutionListeners(inheritListeners = false)
@CucumberOptions(plugin = {"json:target/cucumber-report.json",
                           "html:target",
                           "summary", "pretty"},
    tags = {"@demo"},
    features = "src/test/resources/features")
public class RunCucumberTest extends AbstractTestNGCucumberTests {
  
  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }
  
}
