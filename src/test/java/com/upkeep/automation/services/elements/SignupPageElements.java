package com.upkeep.automation.services.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public enum SignupPageElements {

	SIGN_UP_FORM(By.xpath("//form[@name='signupForm']"), ""),
	EMAIL_INPUT(By.id("signup_username"), ""),
	PASSWORD_INPUT(By.id("signup_username"), ""),
	FIRST_NAME_INPUT(By.id("signup_firstname"), ""),
	LAST_NAME_INPUT(By.id("signup_lastname"), ""),
	PHONE_NUMBER(By.id("signup_companyPhone"), "");

	private By selector;
	private String query;
}
