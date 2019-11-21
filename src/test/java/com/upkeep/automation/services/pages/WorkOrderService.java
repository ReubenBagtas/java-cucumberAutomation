package com.upkeep.automation.services.pages;

import com.upkeep.automation.services.elements.NavbarElements;
import com.upkeep.automation.services.helpers.AutomationWait;
import com.upkeep.automation.services.helpers.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.CREATE_WORK_ORDER_BUTTON;
import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.FORM_WORK_ORDER_TITLE;
import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.FORM_CREATE_WORK_ORDER_BUTTON;
import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.WORK_ORDER_WRAPPER_CLOSE_BUTTON;
import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.SUCCESS_MODAL_CLOSE_BUTTON;
import static com.upkeep.automation.services.elements.WorkOrdersDashboardElements.WORK_ORDERS_HEADER;

@Component
public class WorkOrderService {

  private final BrowserService browserService;
  private final AutomationWait defaultWait;
  private final AutomationWait reactionWait;

  @Autowired
  public WorkOrderService(BrowserService browserService,
                          @Qualifier("defaultWait") AutomationWait defaultWait,
                          @Qualifier("reactionWait") AutomationWait reactionWait) {
    this.browserService = browserService;
    this.defaultWait = defaultWait;
    this.reactionWait = reactionWait;
  }

  public void createWorkOrder() {
    this.browserService.isDisplayed(CREATE_WORK_ORDER_BUTTON.getSelector());
    this.browserService.click(CREATE_WORK_ORDER_BUTTON.getSelector());
    this.browserService.setTextField(FORM_WORK_ORDER_TITLE.getSelector(), "test");
    this.browserService.click(FORM_CREATE_WORK_ORDER_BUTTON.getSelector());
    this.browserService.isDisplayed(SUCCESS_MODAL_CLOSE_BUTTON.getSelector());
    this.browserService.click(SUCCESS_MODAL_CLOSE_BUTTON.getSelector());
    this.browserService.isDisplayed(WORK_ORDER_WRAPPER_CLOSE_BUTTON.getSelector());
    //todo add step to store wo# (work order number) for later use
    this.browserService.click(NavbarElements.WORK_ORDERS.getSelector());
    this.browserService.isDisplayed(CREATE_WORK_ORDER_BUTTON.getSelector());
  }

  public void waitUntilWorkOrderPageIsDisplayed() {
    /**
     * Wait for Work Order page to display
     */
    browserService.isDisplayed(WORK_ORDERS_HEADER.getSelector());
  }

  public void openWorkOrderForm() {
    /**
     * Opens the Work Order Form
     */
    browserService.isDisplayed(CREATE_WORK_ORDER_BUTTON.getSelector());
    browserService.click(CREATE_WORK_ORDER_BUTTON.getSelector());
  }

  public void fillWorkOrderForm() {
    /**
     * Fills the Work Order Form
     */
    browserService.setTextField(FORM_WORK_ORDER_TITLE.getSelector(), "test");
  }

  public void submitWorkOrderForm() {
    /**
     * Submits Work Order Form
     */
    defaultWait.untilElementVisible(FORM_CREATE_WORK_ORDER_BUTTON.getSelector());
    browserService.isDisplayed(FORM_CREATE_WORK_ORDER_BUTTON.getSelector());
    browserService.click(FORM_CREATE_WORK_ORDER_BUTTON.getSelector());
    browserService.isDisplayed(SUCCESS_MODAL_CLOSE_BUTTON.getSelector());
    browserService.isNotDisplayed(SUCCESS_MODAL_CLOSE_BUTTON.getSelector());
  }

  public String getOrderIdFromWorkOrderOverview() {
    /**
     * Retrieves the Work Order Id from the Work Order Wrapper
     * Returns WorkOrderId
     */
    // Grab Work Order Text
    // Method to parse through text and only grab ID
    String workOrderId = "";
    return workOrderId;
  }

  public void closeWorkOrderWrapper() {
    /**
     * Closes the Work Order Wrapper
     */
    browserService.isDisplayed(WORK_ORDER_WRAPPER_CLOSE_BUTTON.getSelector());
    browserService.click(WORK_ORDER_WRAPPER_CLOSE_BUTTON.getSelector());
  }
}
