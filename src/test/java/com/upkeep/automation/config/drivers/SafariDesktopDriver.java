package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
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

@Configuration
@Profile("safari.desktop")
@Slf4j
public class SafariDesktopDriver {
  private static final Dimension DESKTOP_SIZE = new Dimension(1600, 1200);

  @Bean
  public WebDriver driver(Environment environment, SafariOptions capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    WebDriver driver;

    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(
          driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL.getLiteral() : GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
    } else {
      driver = new SafariDriver(capabilities);
    }

    driver.manage().window().setSize(DESKTOP_SIZE);

    return driver;
  }

  @Bean
  public SafariOptions capabilities(Environment environment) {
    final SafariOptions capabilities = new SafariOptions();
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      String osPlatform = environment.getProperty(DESKTOP_DEFAULT_PLATFORM.getLiteral()).replace("_", " ");
    //      capabilities.setCapability("platform", osPlatform);
    //      capabilities.setCapability("version",
    //          environment.getProperty(DEFAULT_BROWSER_VERSION.getLiteral()));
    //      capabilities.setCapability("screenResolution", "1600x1200");
    //      capabilities.setCapability("tunnelIdentifier",
    //          environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //      capabilities.setCapability("videoUploadOnPass",
    //              environment.getProperty(SAUCE_UPLOAD_PASSING.getLiteral()));
    //    }

    return capabilities;
  }
}
