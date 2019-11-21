package com.upkeep.automation.services.pages;

import com.upkeep.automation.components.TestProperties;
import com.upkeep.automation.config.PropertyKeys;
import com.upkeep.automation.services.elements.LoginPageElements;
import com.upkeep.automation.services.elements.HomePageElements;
import com.upkeep.automation.services.helpers.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPageService {

  private final BrowserService browserService;
  private final TestProperties testProperties;

  @Autowired
  public LoginPageService(
          BrowserService browserService,
          TestProperties testProperties) {
    this.browserService = browserService;
    this.testProperties = testProperties;
  }

  public void login(String state, String role) {
    boolean isValid = "valid".equalsIgnoreCase(state);
    // Modify Role based on role value
    browserService.setTextField(LoginPageElements.EMAIL_FIELD.getSelector(), giveMeRole(role));
    browserService.setTextField(LoginPageElements.PASSWORD_FIELD.getSelector(), giveMePassword(role));
    browserService.click(LoginPageElements.LOGIN_BUTTON.getSelector());
  }

  private String giveMeRole(String role) {
    return testProperties.getProperty(PropertyKeys.ADMIN_EMAIL.getLiteral());
  }

  private String giveMePassword(String role) {
    return testProperties.getProperty(PropertyKeys.ADMIN_PASSWORD.getLiteral());
  }

  public void logout() {
    browserService.isDisplayed(HomePageElements.PROFILE_DROPDOWN.getSelector());
    browserService.click(HomePageElements.PROFILE_DROPDOWN.getSelector());
    browserService.isDisplayed(HomePageElements.LOGOUT_BUTTON.getSelector());
    browserService.click(HomePageElements.LOGOUT_BUTTON.getSelector());
  }

  public boolean userIsOnLoginPage() {
    return browserService.isDisplayed(LoginPageElements.LOGIN_BUTTON.getSelector());
  }

}
