package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import static com.upkeep.automation.config.DriverType.SAUCE_LABS;
import static com.upkeep.automation.config.PropertyKeys.GRID_URL;
import static com.upkeep.automation.config.PropertyKeys.SAUCE_URL;
import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

@Configuration
@Profile("chrome.desktop")
@Slf4j
public class ChromeDesktopDriver {
  private static final Dimension DESKTOP_SIZE = new Dimension(1280, 1024);

  @Bean
  public WebDriver driver(Environment environment, ChromeOptions capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    WebDriver driver;

    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(
          driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL.getLiteral() : GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
    } else {
      driver = new ChromeDriver(capabilities);
    }

    driver.manage().window().setSize(DESKTOP_SIZE);

    return driver;
  }

  @Bean
  public ChromeOptions capabilities(Environment environment) {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("allow-running-insecure-content");
    chromeOptions.addArguments("test-type");
    chromeOptions.addArguments("disable-infobars");
    chromeOptions.setCapability(CAPABILITY, chromeOptions);
    chromeOptions.setCapability("acceptInsecureCerts", true);
    chromeOptions.setCapability("acceptSslCerts", true);

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      String osPlatform = environment.getProperty(DESKTOP_DEFAULT_PLATFORM.getLiteral()).replace("_", " ");
    //      chromeOptions.setCapability("platform", osPlatform);
    //      chromeOptions.setCapability("version", environment.getProperty(DEFAULT_BROWSER_VERSION.getLiteral()));
    //      chromeOptions.setCapability("tunnelIdentifier",
    //              environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //      chromeOptions.setCapability("extendedDebugging", "false");
    //      chromeOptions.setCapability("screenResolution", "1600x1200");
    //    }

    return chromeOptions;
  }
}
