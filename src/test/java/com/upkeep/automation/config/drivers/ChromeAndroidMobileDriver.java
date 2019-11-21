package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.PropertyKeys;
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
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static com.upkeep.automation.config.DriverType.LOCAL;
import static com.upkeep.automation.config.DriverType.SAUCE_LABS;
import static com.upkeep.automation.config.PropertyKeys.SAUCE_URL;
import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

@Configuration
@Profile("chrome.android.mobile")
@Slf4j
public class ChromeAndroidMobileDriver {

  @Bean
  public WebDriver driver(Environment environment, ChromeOptions capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    WebDriver driver;

    if (!LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL
          .getLiteral() : PropertyKeys.GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
      driver.manage().window().maximize();
    } else {
      driver = new ChromeDriver(capabilities);
    }
    return driver;
  }

  @Bean
  public ChromeOptions capabilities(Environment environment) {

    String mobileUserAgent = environment.getProperty(PropertyKeys.MOBILE_USER_AGENT.getLiteral());
    ChromeOptions chromeOptions = new ChromeOptions();

    Map<String, Object> deviceMetrics = new HashMap<>();
    deviceMetrics.put("width", 360);
    deviceMetrics.put("height", 640);
    deviceMetrics.put("pixelRatio", 3.0);

    Map<String, Object> mobileEmulation = new HashMap<>();
    mobileEmulation.put("deviceMetrics", deviceMetrics);
    mobileEmulation.put("userAgent", mobileUserAgent);

    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
    chromeOptions.addArguments("allow-running-insecure-content");
    chromeOptions.addArguments("test-type");
    chromeOptions.addArguments("disable-infobars");
    chromeOptions.setCapability(CAPABILITY, chromeOptions);

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      chromeOptions.setCapability("tunnelIdentifier",
    //          environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //      chromeOptions.setCapability("extendedDebugging", "false");
    //    }

    return chromeOptions;
  }
}
