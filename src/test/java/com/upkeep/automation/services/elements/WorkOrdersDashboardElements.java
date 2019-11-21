package com.upkeep.automation.services.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public enum WorkOrdersDashboardElements {

    WORK_ORDERS_HEADER(By.cssSelector("[class=_test-page-header-title]"), ""),
    CREATE_WORK_ORDER_BUTTON(By.xpath("//button[text()='Create Work Order']"), ""),
    FORM_WORK_ORDER_TITLE(By.xpath("//form//input[@class='_test-field-title']"), ""),
    FORM_CREATE_WORK_ORDER_BUTTON(By.xpath("//button[text()='Create Work Order' and @type='submit']"), ""),
    WORK_ORDER_WRAPPER_CLOSE_BUTTON(By.xpath("//span//*[@xmlns][2]"), ""),
    SUCCESS_MODAL_CLOSE_BUTTON(By.xpath("//span[contains(@class,'CloseButton')]"), "");

  private By selector;
  private String query;
}
