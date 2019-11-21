package com.upkeep.automation.cucumber.steps;

import com.upkeep.automation.components.TestProperties;
import com.upkeep.automation.services.pages.LoginPageService;
import com.upkeep.automation.services.helpers.BrowserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// Used to suppress warnings about Given When Then.
// New version not supported for linking features to steps in intellij.
@SuppressWarnings("deprecation")
public class CommonSteps {
  private final BrowserService browserService;
  private final TestProperties testProperties;
  private final LoginPageService loginPageService;
  
  @Autowired
  public CommonSteps(LoginPageService loginPageService, BrowserService browserService,
                     TestProperties testProperties) {
    this.loginPageService = loginPageService;
    this.browserService = browserService;
    this.testProperties = testProperties;
  }

  @Given("^I navigate to the (.*?) home page$")
  public void navigateToEvent(String domain) throws Throwable {
    browserService.navigate("http://" + domain + ".com", Collections.emptyMap());
  }

  @Then("I should see the (.*?) home page")
  public void i_should_see_the_google_home_page(String domain) throws Throwable {
    log.info("blah");
  }

  @Given("user is on the login page")
  public void userIsOnTheLoginPage() {
    browserService.navigate(testProperties.getBaseUrl(), Collections.EMPTY_MAP);
  }

  @Then("user is directed to the login page")
  public void userIsDirectedToTheLoginPage() throws Exception {
    if (!loginPageService.userIsOnLoginPage()) {
      throw new Exception("User is not on the login page");
    }
  }

  @Given("user is logged in")
  public void userIsLoggedIn() {
    this.userIsOnTheLoginPage();
    loginPageService.login("valid", "admin");
  }

  @Then("throw an error")
  public void throwAnError() throws Exception {
    throw new Exception("I blame Gary for all of this");
  }
}
