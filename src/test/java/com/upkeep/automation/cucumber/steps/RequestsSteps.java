package com.upkeep.automation.cucumber.steps;

import com.upkeep.automation.services.helpers.RequestsService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
public class RequestsSteps {
  private final RequestsService requestsService;
  private final com.upkeep.automation.services.helpers.UINavigationService UINavigationService;

  @Autowired
  public RequestsSteps(RequestsService requestsService,
             com.upkeep.automation.services.helpers.UINavigationService UINavigationService) {
    this.requestsService = requestsService;
    this.UINavigationService = UINavigationService;
  }


  @Given("a Request is created")
  public void aRequestIsCreated() {
    //Navigate to Request page
    UINavigationService.navigate("Requests");
    //Click create work order
    //add title to work order
    //click add work order button
    //close work order panel (lands on work order dash)
    requestsService.createRequest();
  }

  @Then("Request is visible on the Requests dashboard")
  public void requestIsVisibleOnTheRequestsDashboard() {
  }
}
