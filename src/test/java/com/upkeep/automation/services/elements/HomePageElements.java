package com.upkeep.automation.services.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public enum HomePageElements {

  LOGIN_BUTTON(By.cssSelector(""), ""),
  PROFILE_DROPDOWN(By.xpath("//label[@class='_test-page-header-title']/..//span[contains(@class,'Dropdown')]"),
          ""),
  VIEW_PROFILE(By.xpath("//span[text()='View Profile']"), ""),
  VIEW_COMPANY_PROFILE(By.xpath("//span[text()='View Company Profile']"), ""),
  LOGOUT_BUTTON(By.xpath("//span[text()='Log Out']"), "");

  private By selector;
  private String query;
}
