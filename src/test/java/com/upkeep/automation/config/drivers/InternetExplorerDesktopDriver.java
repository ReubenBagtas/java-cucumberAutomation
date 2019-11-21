package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
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
import static com.upkeep.automation.config.PropertyKeys.DRIVER_TYPE;
import static com.upkeep.automation.config.PropertyKeys.SAUCE_URL;
import static com.upkeep.automation.config.PropertyKeys.GRID_URL;

@Configuration
@Profile("ie.desktop")
@Slf4j
public class InternetExplorerDesktopDriver {
  private static final Dimension DESKTOP_SIZE = new Dimension(1280, 1024);

  @Bean
  public WebDriver driver(Environment environment, InternetExplorerOptions capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(DRIVER_TYPE.getLiteral());
    WebDriver driver;

    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(
          driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL.getLiteral() : GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
    } else {
      driver = new InternetExplorerDriver(capabilities);
    }

    driver.manage().window().setSize(DESKTOP_SIZE);

    return driver;
  }

  @Bean
  public InternetExplorerOptions capabilities(Environment environment) {
    final InternetExplorerOptions capabilities = new InternetExplorerOptions();

    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    capabilities.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
    capabilities.setCapability("javascriptEnabled", true);
    capabilities.setCapability("ignoreZoomSetting", true);
    capabilities.setCapability("ignoreProtectedModeSettings", true);
    capabilities.setCapability("seleniumVersion", "3.4.0");
    capabilities.setCapability("version", "11.0");

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      String osPlatform = environment.getProperty(DESKTOP_DEFAULT_PLATFORM.getLiteral()).replace("_", " ");
    //      capabilities.setCapability("platform", osPlatform);
    //      capabilities.setCapability("version", environment.getProperty(DEFAULT_BROWSER_VERSION.getLiteral()));
    //      capabilities.setCapability("screenResolution", "1280x1024");
    //      capabilities.setCapability("tunnelIdentifier",
    //              environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //      capabilities.setCapability("videoUploadOnPass",
    //              environment.getProperty(SAUCE_UPLOAD_PASSING.getLiteral()));
    //    }

    return capabilities;
  }
}
