package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
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
import static com.upkeep.automation.config.PropertyKeys.GRID_URL;
import static com.upkeep.automation.config.PropertyKeys.SAUCE_URL;
import static org.openqa.selenium.firefox.FirefoxDriver.MARIONETTE;
import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;

@Configuration
@Profile("firefox.desktop")
@Slf4j
public class FirefoxDesktopDriver {
  private static final Dimension DESKTOP_SIZE = new Dimension(1600, 1200);

  @Bean
  public WebDriver driver(Environment environment, FirefoxOptions capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    WebDriver driver;

    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(
          driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL.getLiteral() : GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
    } else {
      driver = new FirefoxDriver(capabilities);
    }

    driver.manage().window().setSize(DESKTOP_SIZE);

    return driver;
  }

  @Bean
  public FirefoxOptions capabilities(Environment environment) {
    final FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("security.mixed_content.block_active_content", false);
    profile.setPreference("dom.max_script_run_time", 0);
    profile.setPreference("dom.max_chrome_script_run_time", 0);
    profile.setPreference("overlappingCheckDisabled", true);
    final FirefoxOptions capabilities = new FirefoxOptions();

    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    capabilities.setCapability("acceptInsecureCerts", true);
    capabilities.setCapability("javascriptEnabled", true);
    capabilities.setCapability("version", "62.0");
    capabilities.setCapability("seleniumVersion", "3.14.0");

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      String osPlatform = environment.getProperty(DESKTOP_DEFAULT_PLATFORM.getLiteral()).replace("_", " ");
    //      capabilities.setCapability("platform", osPlatform);
    //      capabilities.setCapability("version",
    //          environment.getProperty(DEFAULT_BROWSER_VERSION.getLiteral()));
    //      capabilities.setCapability("screenResolution", "1600x1200");
    //      capabilities.setCapability("tunnelIdentifier",
    //          environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //    }

    capabilities.setCapability(PROFILE, profile);
    capabilities.setCapability(MARIONETTE, true);

    return capabilities;
  }
}
