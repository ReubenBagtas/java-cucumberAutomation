package com.upkeep.automation.cucumber.steps;

import com.upkeep.automation.services.pages.SignupPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class SignupSteps {

	private final SignupPageService signupPageService;

  public SignupSteps(SignupPageService signupPageService) {

		this.signupPageService = signupPageService;
	}

	@Given("user is on the signup page")
	public void userIsOnTheSignupPage() {
		signupPageService.navigate_to_signup_page();
		signupPageService.wait_until_signup_page_is_displayed();
	}

	@When("user signs up")
	public void userSignsUp() {
		//fill signup form
		//submit signup form
		//verify signup toast modal displays
	}
}
