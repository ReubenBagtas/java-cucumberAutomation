package com.upkeep.automation.services.helpers;

import com.upkeep.automation.selenium.AutomationWait;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BrowserService {

  private static final Logger LOG = LoggerFactory.getLogger(BrowserService.class);

  private final WebDriver webDriver;
  private final AutomationWait defaultWait;
  private final AutomationWait reactionWait;
  private final JQueryService jQueryService;
  private final Actions actions;
  
  @Autowired
  public BrowserService(
      WebDriver webDriver,
      @Qualifier("defaultWait") AutomationWait defaultWait,
      @Qualifier("reactionWait") AutomationWait reactionWait,
      JQueryService jQueryService) {
    this.webDriver = webDriver;
    this.defaultWait = defaultWait;
    this.reactionWait = reactionWait;
    this.jQueryService = jQueryService;
    this.actions = new Actions(webDriver);
  }

  public void back() {
    webDriver.navigate().back();
  }

  public void refresh() {
    webDriver.navigate().refresh();
  }

  public void navigate(String urlPath, Map<String, String> queryParams) {
    String searchString = "";
    if (queryParams.size() > 0) {
      searchString = "?" + queryParams.entrySet()
                                      .stream()
                                      .map((entry) -> entry.getKey() + "=" + entry.getValue())
                                      .collect(Collectors.joining("&"));
    }

    try {
      webDriver.navigate().to(urlPath + searchString);
    } catch (WebDriverException e) {
      LOG.info(e.toString());
    }
  }

  public void clickOffsetOfElement(By element, int offsetX, int offsetY) {
    WebElement elementToclick = defaultWait.untilElementVisible(element);
    actions.moveToElement(elementToclick).moveByOffset(offsetX, offsetY).click().build().perform();
  }

  public void clickJS(By locator) {
    try {
      clickJS(defaultWait.untilElementClickable(locator));
    } catch (WebDriverException e) {
      LOG.info(e.toString());
    }
  }

  public void clickNative(By locator) {
    defaultWait.untilElementClickable(locator);
    webDriver.findElement(locator).click();
  }

  // Required for transparent element clicking
  public void click(By locator) {
    webDriver.findElement(locator).click();
  }

  public void actionClick(WebElement webElement) {
    actions.moveToElement(webElement).click(webElement).build().perform();
  }

  public void fullActionClick(WebElement webElement) {
    actions.clickAndHold(webElement).release(webElement).build().perform();
  }

  public void hover(WebElement webElement) {
    actions.moveToElement(webElement).moveToElement(webElement).build().perform();
  }

  public String getElementValue(By locator) {
    return getElementValue(defaultWait.untilElementClickable(locator), true);
  }

  public boolean isDisplayed(By locator) {
    try {
      defaultWait.untilElementVisible(locator);
    } catch (TimeoutException timeout) {
      return false;
    }

    return isDisplayedNow(locator);
  }

  public boolean isDisplayedMulti(By locator) {
    try {
      defaultWait.untilElementVisible(locator);
      return webDriver.findElement(locator).isDisplayed();
    } catch (TimeoutException timeout) {
      return false;
    }
  }

  public boolean isDisplayedNow(By locator) {
    List<WebElement> elements = getElements(locator);

    if (elements.size() == 0) {
      return false;
    } else if (elements.size() > 1) {
      throw new IllegalStateException(
          "Exactly one " + locator.toString() + " should be present, but found: " + elements.size());
    } else {
      return elements.get(0).isDisplayed();
    }
  }

  public boolean isNotDisplayed(By locator) {
    try {
      return reactionWait.untilElementNotVisible(locator);
    } catch (Exception e) {
      return !webDriver.findElement(locator).isDisplayed();
    }
  }

  public String getPageSource() {
    return webDriver.getPageSource();
  }

  public String getTitle() {
    return webDriver.getTitle();
  }

  public String getCurrentUrl() {
    return webDriver.getCurrentUrl();
  }

  public Cookie getCookie(String cookieName) {
    return webDriver.manage().getCookieNamed(cookieName);
  }

  public String getText(By elementLocator) {
    defaultWait.untilElementClickable(elementLocator);
    return getElement(elementLocator).getText();
  }

  public String getCssValue(By elementLocator, String propertyName) {
    defaultWait.untilElementClickable(elementLocator);
    return getElement(elementLocator).getCssValue(propertyName);
  }

  public String getAttribute(By elementLocator, String attributeName) {
    return getElement(elementLocator).getAttribute(attributeName);
  }

  /**
   * Returns list of elements found.
   * Returns list size of 0 if it cannot find the elements.
   * */
  public List<WebElement> getElements(By selector) {
    return webDriver.findElements(selector);
  }

  /**
   * Returns list of visible elements.
   * FAILS if it cannot find visible elements.
   * */
  public List<WebElement> getVisibleElements(By selector) {
    defaultWait.untilElementVisible(selector);

    List<WebElement> elements = webDriver.findElements(selector);
    if (elements.size() > 0) {
      List<WebElement> visibleElements = elements
          .stream()
          .filter(element -> element.isDisplayed())
          .collect(Collectors.toList());

      if (visibleElements.size() > 0) {
        return visibleElements;
      } else {
        throw new IllegalStateException("Could not find visible elements: " + selector);
      }
    } else {
      throw new IllegalStateException("Could not find selector: " + selector);
    }
  }

  public void setTextField(By locator, String input) {
    WebElement webElement = getVisibleElement(defaultWait.untilElementClickable(locator));
    webElement.sendKeys(input);
  }

  public void setValueDropDownBox(By locator, String value) {
    WebElement webElement = defaultWait.untilElementClickable(locator);
    Select selectList = new Select(webElement);
    selectList.selectByValue(value);
  }

  public void setTextDropDownBox(By locator, String value) {
    WebElement webElement = defaultWait.untilElementClickable(locator);
    Select selectList = new Select(webElement);
    selectList.selectByVisibleText(value);
  }

  public boolean isAttributePresent(By locator, String attribute) {
    Boolean result = false;
    try {
      String value = getVisibleElementAttribute(defaultWait.untilElementVisible(locator), attribute);
      if (value != null) {
        result = true;
      }
    } catch (Exception e) {
      result = false;
    }

    return result;
  }

  public boolean isAttributePresent(WebElement locator, String attribute) {
    Boolean result = false;
    try {
      defaultWait.untilElementVisible(locator);
      String value = locator.getAttribute(attribute);
      if (value != null) {
        result = true;
      }
    } catch (Exception e) {
      result = false;
    }

    return result;
  }

  public String getAttributeOfVisibleElement(By locator, String attribute) {
    return getVisibleElementAttribute(defaultWait.untilElementVisible(locator), attribute);
  }

  public boolean isSelected(By locator) {
    return defaultWait.untilElementClickable(locator).isSelected();
  }

  public String getElementValueOrDefault(By locator, String defaultValue) {
    try {
      return webDriver.findElement(locator).getText();
    } catch (NoSuchElementException | TimeoutException e) {
      return defaultValue;
    }
  }

  public void clickJS(WebElement webElement) {
    jQueryService.click(webElement);
  }

  public void key(Keys key) {
    actions.sendKeys(key).perform();
  }

  public boolean isFocused(By locator) {
    return webDriver.findElement(locator).equals(webDriver.switchTo().activeElement());
  }

  public WebElement getElement(By elementLocator) {
    defaultWait.untilElementExists(elementLocator);
    return webDriver.findElement(elementLocator);
  }

  public boolean existsOnPage(By locator) {
    List<WebElement> elements = getElements(locator);
    return (elements.size() != 0);
  }

  public void simulateSwipeByDraggingMouse(WebElement startElement, int xOffset, int yOffset) {
    Action swipe = actions.clickAndHold(startElement)
                          .moveByOffset(xOffset, yOffset)
                          .release()
                          .build();
    swipe.perform();
  }

  public void switchFrame(By locator) {
    defaultWait.getWebDriverWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
  }

  public void exitFrame() {
    webDriver.switchTo().defaultContent();
  }

  public void clearField(By locator) {
    getVisibleElement(defaultWait.untilElementClickable(locator)).clear();
  }

  public void scrollToPageTop() {
    jQueryService.executeJavascript("window.scrollBy(0,-document.body.scrollHeight)");
  }

  public void scrollToElement(By element) {
    WebElement elem = webDriver.findElement(element);
    scrollToElement(elem);
  }

  public void scrollToElement(WebElement element) {
    jQueryService.executeJavascript("arguments[0].scrollIntoView(true);", element);
  }

  private String getElementScrollHeight(By locator) {
    return jQueryService.executeJavascript("return arguments[0].scrollHeight;",
        defaultWait.untilElementExists(locator)).toString();
  }

  private WebElement getVisibleElement(WebElement webElement) {
    if (webElement.isDisplayed()) {
      return webElement;
    } else {
      return defaultWait.untilElementVisible(webElement);
    }
  }

  private String getVisibleElementAttribute(WebElement visibleWebElement, String attribute) {
    return visibleWebElement.getAttribute(attribute);
  }

  public String getElementValue(WebElement webElement) {
    return getElementValue(webElement, true);
  }

  public String getElementValue(WebElement webElement, boolean requireIsVisible) {
    String elementText = null;
    String tagName = webElement.getTagName();
    WebElement element = (requireIsVisible) ? getVisibleElement(webElement) : webElement;

    switch (tagName) {
      case "select":
        Select selectList = new Select(webElement);
        elementText = selectList.getFirstSelectedOption().getText();
        break;
      case "input":
        String type = element.getAttribute("type");
        type = StringUtils.lowerCase(type);
        if ("text".equals(type) || "button".equals(type) || "password".equals(type)) {
          elementText = element.getAttribute("value");
        }
        break;
      case "abbr":
        elementText = element.getAttribute("title");
        break;
      default:
        elementText = element.getText();
        break;
    }

    return elementText;
  }

  public void setCookie(String name, String value) {
    webDriver.manage().addCookie(new Cookie(name, value));
  }

  public void deleteCookie(String name) {
    webDriver.manage().deleteCookieNamed(name);
  }

  public void clearLocalStorage() {
    jQueryService.executeJavascript("localStorage.clear()");
  }
}
