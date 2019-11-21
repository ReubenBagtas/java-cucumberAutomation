package com.upkeep.automation.services.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public enum LoginPageElements {
  EMAIL_FIELD(By.id("username"), ""),
  PASSWORD_FIELD(By.id("password"), ""),
  LOGIN_BUTTON(By.xpath("//form/button"), "");

  private By selector;
  private String query;
}
