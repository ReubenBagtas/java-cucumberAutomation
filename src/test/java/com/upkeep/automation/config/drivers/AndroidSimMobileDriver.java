package com.upkeep.automation.config.drivers;

import com.upkeep.automation.components.TestProperties;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;

import static com.upkeep.automation.config.DriverType.SAUCE_LABS;
import static com.upkeep.automation.config.PropertyKeys.ANDROID_BROWSER;
import static com.upkeep.automation.config.PropertyKeys.ANDROID_DEVICE_NAME;
import static com.upkeep.automation.config.PropertyKeys.ANDROID_PLATFORM_NAME;
import static com.upkeep.automation.config.PropertyKeys.ANDROID_PLATFORM_VERSION;
import static com.upkeep.automation.config.PropertyKeys.DEVICE_ORIENTATION;
import static com.upkeep.automation.config.PropertyKeys.DRIVER_TYPE;
import static com.upkeep.automation.config.PropertyKeys.SAUCE_URL;
import static com.upkeep.automation.config.PropertyKeys.APPIUM_URL;
import static com.upkeep.automation.config.PropertyKeys.APPIUM_VERSION;

@Configuration
@Profile("android.sim.mobile")
@Slf4j
public class AndroidSimMobileDriver {

  private TestProperties testProperties;

  @Autowired
  public AndroidSimMobileDriver(TestProperties testProperties) {
    this.testProperties = testProperties;
  }

  @Bean
  public AppiumDriver<? extends MobileElement> driver(Environment environment,
                                                      DesiredCapabilities capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(DRIVER_TYPE.getLiteral());
    AppiumDriver driver;

    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ?
              SAUCE_URL.getLiteral() : APPIUM_URL.getLiteral());
      driver = new AndroidDriver(new URL(gridURL), capabilities);
    } else {
      driver = new AndroidDriver(new URL(testProperties.getProperty(APPIUM_URL.getLiteral())), capabilities);
    }
    return driver;
  }

  @Bean
  public DesiredCapabilities capabilities(Environment environment) {

    DesiredCapabilities caps = DesiredCapabilities.android();
    caps.setCapability("appiumVersion", testProperties.getProperty(APPIUM_VERSION.getLiteral()));
    caps.setCapability("deviceName", testProperties.getProperty(ANDROID_DEVICE_NAME.getLiteral()));
    caps.setCapability("deviceOrientation", testProperties.getProperty(DEVICE_ORIENTATION.getLiteral()));
    caps.setCapability("browserName", testProperties.getProperty(ANDROID_BROWSER.getLiteral()));
    caps.setCapability("platformVersion",
            testProperties.getProperty(ANDROID_PLATFORM_VERSION.getLiteral()));
    caps.setCapability("platformName", testProperties.getProperty(ANDROID_PLATFORM_NAME.getLiteral()));
    caps.setCapability("noReset", true);

    //    if (environment.getProperty(DRIVER_TYPE.getLiteral()).equalsIgnoreCase(SAUCE_LABS.getName())) {
    //      caps.setCapability("tunnelIdentifier",
    //          environment.getProperty(SAUCE_LABS_TUNNEL_NAME.getLiteral()));
    //    }

    return caps;
  }

}
