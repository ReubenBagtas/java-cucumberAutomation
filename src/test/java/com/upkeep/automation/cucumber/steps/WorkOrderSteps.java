package com.upkeep.automation.cucumber.steps;

import com.upkeep.automation.components.ValidationCommands;
import com.upkeep.automation.services.pages.WorkOrderService;
//import com.sun.corba.se.spi.orbutil.threadpool.Work;
import groovy.util.logging.Slf4j;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Slf4j
@SuppressWarnings("deprecation")
public class WorkOrderSteps {
  private final ValidationCommands validationCommands;
  private final WorkOrderService workOrderService;

  public WorkOrderSteps(ValidationCommands validationCommands, WorkOrderService workOrderService) {
    this.validationCommands = validationCommands;
    this.workOrderService = workOrderService;
  }

  @Given("a Work Order is created")
  public void aWorkOrderIsCreated() {
    workOrderService.openWorkOrderForm();
    workOrderService.fillWorkOrderForm();
    workOrderService.submitWorkOrderForm();
    workOrderService.closeWorkOrderWrapper();
  }

  @Then("Work Order is visible on the Work Order dashboard")
  public void workOrderIsVisibleOnTheWorkOrderDashboard() {
  }
}
