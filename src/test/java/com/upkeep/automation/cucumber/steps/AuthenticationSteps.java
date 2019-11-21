package com.upkeep.automation.cucumber.steps;

import com.upkeep.automation.components.ValidationCommands;
import com.upkeep.automation.services.pages.LoginPageService;
import com.upkeep.automation.services.helpers.BrowserService;
import cucumber.api.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.WORK_ORDERS_HEADER;

@SuppressWarnings("deprecation")
public class AuthenticationSteps {
  private final LoginPageService loginPageService;
  private final ValidationCommands validationCommands;
  private final BrowserService browserService;

  public AuthenticationSteps(LoginPageService loginPageService,
                             ValidationCommands validationCommands,
                             BrowserService browserService) {
    this.loginPageService = loginPageService;
    this.validationCommands = validationCommands;
    this.browserService = browserService;
  }
  
  @And("^user logs in with (.*?) (.*?) credentials$")
  public void userLogsInWithValidAdminCredentials(String state, String role) {
    loginPageService.login(state, role);
  }
  
  @Then("Work Orders dashboard is displayed")
  public void workOrdersDashboardIsDisplayed() {
    validationCommands.elementTextEquals(WORK_ORDERS_HEADER.getSelector(),
        "Work Orders", false);
  }


  @When("user logs out")
  public void userLogsOut() {
    loginPageService.logout();
  }

}
