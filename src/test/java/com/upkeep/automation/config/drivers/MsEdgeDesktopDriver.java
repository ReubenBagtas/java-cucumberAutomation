package com.upkeep.automation.config.drivers;

import com.upkeep.automation.config.DriverType;
import com.upkeep.automation.config.PropertyKeys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
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
@Profile("msedge.desktop")
@Slf4j
public class MsEdgeDesktopDriver {
  private static final Dimension DESKTOP_SIZE = new Dimension(1280, 1024);
  
  @Autowired
  private Environment environment;
  
  @Bean
  public WebDriver driver(Environment environment,
                          DesiredCapabilities capabilities) throws MalformedURLException {
    String driverType = environment.getProperty(PropertyKeys.DRIVER_TYPE.getLiteral());
    WebDriver driver;
    
    if (!DriverType.LOCAL.getName().equalsIgnoreCase(driverType)) {
      String gridURL = environment.getProperty(
          driverType.equalsIgnoreCase(SAUCE_LABS.getName()) ? SAUCE_URL.getLiteral() : GRID_URL.getLiteral());
      driver = new RemoteWebDriver(new HttpCommandExecutor(new URL(gridURL)), capabilities);
      driver.manage().window().maximize();
    } else {
      driver = new EdgeDriver(capabilities);
      driver.manage().window().setSize(DESKTOP_SIZE);
    }
    return driver;
  }
  
  @Bean
  public DesiredCapabilities capabilities(Environment environment) {
    final DesiredCapabilities capabilities = DesiredCapabilities.edge();
    capabilities.setCapability("platform", "Windows 10");
    capabilities.setCapability("version", "16.16299");
    capabilities.setCapability("InPrivate", true);
    
    return capabilities;
  }

}
