package com.upkeep.automation.components;

import com.upkeep.automation.selenium.AutomationWait;
import com.upkeep.automation.services.helpers.BrowserService;


import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Component
public class ValidationCommands {
  
  
  private final BrowserService browserService;
  private final AutomationWait defaultWait;
  
  @Autowired
  public ValidationCommands(BrowserService browserService,
                            @Qualifier("defaultWait") AutomationWait defaultWait) {
    this.browserService = browserService;
    this.defaultWait = defaultWait;
  }
  
  public void elementAttributeContains(By locator, String attribute, String expectedAttributeValue) {
    String actualAttributeValue = browserService.getAttribute(locator, attribute);
    boolean result = actualAttributeValue.contains(expectedAttributeValue);
    
    String errorMsg =
        "The Actual Attribute [" + attribute + "] has value [" + actualAttributeValue
        + "] and does not contain expected Attribute Value [" + expectedAttributeValue + "]";
    assertThat(result).overridingErrorMessage(errorMsg).isTrue();
  }
  
  public void elementAttributeNotContains(By locator, String attribute, String expectedAttributeValue) {
    String actualAttributeValue = browserService.getAttribute(locator, attribute);
    boolean result = !actualAttributeValue.contains(expectedAttributeValue);
    
    String errorMsg =
        "The Actual Attribute [" + attribute + "] has value [" + actualAttributeValue
        + "] and does contain expected Attribute Value [" + expectedAttributeValue + "]";
    assertThat(result).overridingErrorMessage(errorMsg).isTrue();
  }
  
  public void elementTextEquals(By elementLocator, String expectedText, boolean ignoreCase) {
    String actualText = browserService.getElementValue(elementLocator);
    boolean result;
    
    if (ignoreCase) {
      result = actualText.equalsIgnoreCase(expectedText);
    } else {
      result = actualText.equals(expectedText);
    }
    
    String message = "Expected text [" + expectedText + "] is NOT equal to Actual Element Text [" + actualText + "]";
    assertThat(result).overridingErrorMessage(message).isTrue();
  }
  
  public void elementTextGreaterThan(By elementLocator, int expectedMinimum) {
    String actualNumber = browserService.getElementValue(elementLocator);
    
    String message = "Expected number [" + String.valueOf(expectedMinimum)
                     + "] to be less than the actual element value [" + actualNumber + "]";
    assertThat(Integer.parseInt(actualNumber)).overridingErrorMessage(message).isGreaterThan(expectedMinimum);
  }
  
  public void elementTextContains(By elementLocator, String expectedText, boolean ignoreCase) {
    String actualText = browserService.getElementValue(elementLocator);
    boolean result;
    
    if (ignoreCase) {
      result = StringUtils.containsIgnoreCase(actualText, expectedText);
    } else {
      result = StringUtils.contains(actualText, expectedText);
    }
    
    String message = "Expected text: " + expectedText + " is not contained in Actual Element Text: " + actualText;
    assertThat(result).overridingErrorMessage(message).isTrue();
  }
  
  public void elementTextDoesNotContain(By elementLocator, String expectedText, boolean ignoreCase) {
    String actualText = browserService.getElementValue(elementLocator);
    boolean result;
    
    if (ignoreCase) {
      result = StringUtils.containsIgnoreCase(actualText, expectedText);
    } else {
      result = StringUtils.contains(actualText, expectedText);
    }
    
    String message = "Expected text: " + expectedText + " is present in Actual Element Text: " + actualText;
    assertThat(result).overridingErrorMessage(message).isFalse();
  }
  
  /**
   * Validate if exactly ONE element is displayed.
   * Fails if more than ONE element is found or element is not displayed.
   * */
  public void elementIsDisplayed(By locator, boolean visible) {
    if (visible) {
      elementIsDisplayed(locator);
    } else {
      elementIsNotDisplayed(locator);
    }
  }
  
  /**
   * Validate if ONE or MORE elements are displayed.
   * */
  public void elementsAreDisplayed(By locator) {
    List<WebElement> elements = browserService.getVisibleElements(locator);
    
    String errorMsg = "Elements " + locator + " are NOT displayed.";
    assertThat(elements.size()).overridingErrorMessage(errorMsg).isGreaterThan(0);
  }
  
  public void currentUrlContains(String expectedText) {
    boolean isPresent;
    
    try {
      defaultWait.getWebDriverWait().until((WebDriver w) -> w.getCurrentUrl().contains(expectedText));
      isPresent = true;
    } catch (TimeoutException e) {
      
      isPresent = false;
    }
    
    String message = "Expected text [" + expectedText +
                     "] is NOT contained in Actual URL [" + browserService.getCurrentUrl() + "]";
    assertThat(isPresent).overridingErrorMessage(message).isTrue();
  }
  
  public void currentBrowserTitleContains(String expectedText) {
    boolean isPresent;
    
    try {
      defaultWait.getWebDriverWait().until((WebDriver w) -> w.getTitle().contains(expectedText));
      isPresent = true;
    } catch (TimeoutException e) {
      
      isPresent = false;
    }
    
    String message = "YO!! Expected text [" + expectedText +
                     "] is NOT contained in Actual Title [" + browserService.getTitle() + "] BRO!!";
    assertThat(isPresent).overridingErrorMessage(message).isTrue();
    
  }
  
  public void validateElementTextPatternMatches(WebElement webElement, String expectedPattern) {
    String elementText = defaultWait.getWebDriverWait().until((WebDriver w) -> webElement.getText());
    Pattern r = Pattern.compile(expectedPattern);
    Matcher matcher = r.matcher(elementText);
    
    String errorMsg =
        "Element text: " + elementText + " does not match expected pattern " + expectedPattern;
    assertThat(matcher.find()).overridingErrorMessage(errorMsg).isTrue();
  }
  
  /**
   * Validate one of the followings: <br>
   * the element's attribute is equal to the expected attribute value <br>
   * the element's attribute is NOT equal to the expected attribute value <br>
   * the element's attribute contains the expected attribute value <br>
   * the element's attribute does NOT contain the expected attribute value
   *
   * @param locator locator of the element
   * @param attribute the attribute's name
   * @param expectedAttributeValue the expected attribute value
   */
  public void validateElementAttributeEquals(By locator, String attribute, String expectedAttributeValue) {
    String actualAttributeValue = browserService.getAttributeOfVisibleElement(locator, attribute);
    
    String failedMessageWhenExpectedTrue =
        "The Actual Attribute: " + attribute + " has value: " + actualAttributeValue
        + " is not contain or equal expected Attribute Value: " + expectedAttributeValue;
    
    boolean result = StringUtils.equalsIgnoreCase(actualAttributeValue, expectedAttributeValue);
    
    assertThat(result).overridingErrorMessage(failedMessageWhenExpectedTrue).isTrue();
  }
  
  private void elementIsDisplayed(By locator) {
    boolean result;
    try {
      result = browserService.isDisplayed(locator);
    } catch (Throwable t) {
      log.debug(String.format("elementIsNotDisplayed(%s) failed with exception %s", locator, t));
      result = false;
    }
    
    String message = String.format("Element with locator \"%s\" "
                                   + "is expected to be displayed, but it is NOT displayed on the page", locator);
    assertThat(result).overridingErrorMessage(message).isTrue();
  }
  
  public void elementIsNotDisplayed(By locator) {
    boolean result;
    try {
      result = browserService.isNotDisplayed(locator);
    } catch (Throwable t) {
      log.debug(String.format("elementIsNotDisplayed(%s) failed with exception %s", locator, t));
      result = false;
    }
    
    String message = "Element is NOT expected to be displayed, but it is displayed on the page";
    assertThat(result).overridingErrorMessage(message).isTrue();
  }
}
