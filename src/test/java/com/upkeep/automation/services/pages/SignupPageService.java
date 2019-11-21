package com.upkeep.automation.services.pages;

import com.upkeep.automation.components.TestProperties;
import com.upkeep.automation.services.helpers.AutomationWait;
import com.upkeep.automation.services.helpers.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.upkeep.automation.services.elements.SignupPageElements.SIGN_UP_FORM;
import static com.upkeep.automation.services.elements.SignupPageElements.EMAIL_INPUT;
import static com.upkeep.automation.services.elements.SignupPageElements.PASSWORD_INPUT;
import static com.upkeep.automation.services.elements.SignupPageElements.FIRST_NAME_INPUT;
import static com.upkeep.automation.services.elements.SignupPageElements.LAST_NAME_INPUT;
import static com.upkeep.automation.services.elements.SignupPageElements.PHONE_NUMBER;


import java.util.Collections;

@Component
public class SignupPageService {

	private final BrowserService browserService;
	private final TestProperties testProperties;
	private final AutomationWait automationWait;

	@Autowired
	public SignupPageService(BrowserService browserService,
													 TestProperties testProperties,
													 AutomationWait automationWait) {
		this.browserService = browserService;
		this.testProperties = testProperties;
		this.automationWait = automationWait;
	}

	public void navigate_to_signup_page() {
		String signupPath = "#/login/signup";
		String baseUrl = testProperties.getBaseUrl();
		String signupUrl = baseUrl + signupPath;
		browserService.navigate(signupUrl, Collections.EMPTY_MAP);
	}

	public void wait_until_signup_page_is_displayed() {
		automationWait.untilElementVisible(SIGN_UP_FORM.getSelector());
	}

	//todo Add business type selection in the parameter
	public void fill_signup_form(String email,
															 String password,
															 String firstName,
															 String lastnName,
															 String phoneNumber) {
		browserService.
	}
}
