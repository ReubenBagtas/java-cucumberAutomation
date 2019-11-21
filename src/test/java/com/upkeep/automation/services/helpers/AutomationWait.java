package com.upkeep.automation.services.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class AutomationWait {

  private FluentWait<WebDriver> webDriverWait;
  
  public AutomationWait(long maxWaitTimeInSeconds, WebDriver webDriver) {
    webDriverWait = new FluentWait<>(webDriver);
    webDriverWait.withTimeout(Duration.ofSeconds(maxWaitTimeInSeconds))
                 .ignoring(NoSuchElementException.class)
                 .ignoring(StaleElementReferenceException.class)
                 .pollingEvery(Duration.ofMillis(250));
  }

  public FluentWait<WebDriver> getWebDriverWait() {
    return webDriverWait;
  }

  public WebElement untilElementClickable(By locator) {
    return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  public WebElement untilElementClickable(WebElement webElement) {
    return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
  }

  public WebElement untilElementVisible(By locator) {
    return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public Boolean untilElementNotVisible(By locator) {
    return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public WebElement untilElementVisible(WebElement webElement) {
    return webDriverWait.ignoring(StaleElementReferenceException.class)
                        .until(ExpectedConditions.visibilityOf(webElement));
  }

  public WebElement untilElementExists(By locator) {
    return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public List<WebElement> untilAllElementsVisible(By locator) {
    return webDriverWait.ignoring(StaleElementReferenceException.class).until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  public List<WebElement> untilAllElementsPresent(By locator) {
    return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  public WebDriver untilIFrameAvailable(final By by) {
    return webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
  }

  public boolean untilElementEqualCssValue(By locator, String cssName, String value) {
    try {
      webDriverWait.until((WebDriver w) -> w.findElement(locator).getCssValue(cssName).equals(value));
    } catch (TimeoutException te) {
      return false;
    }
    return true;
  }

  public boolean untilTextIsNot(By locator, String expectedText) {
    return webDriverWait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, expectedText)));
  }

  public void untilTitleContains(String expectedTitle) {
    webDriverWait.until(ExpectedConditions.titleContains(expectedTitle));
  }

  public void untilTitleEquals(String expectedTitle) {
    webDriverWait.until(ExpectedConditions.titleIs(expectedTitle));
  }
}

