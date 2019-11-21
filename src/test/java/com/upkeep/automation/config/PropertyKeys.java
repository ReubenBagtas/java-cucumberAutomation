package com.upkeep.automation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum PropertyKeys {

  // Enviroment
  PLATFORM("platform"),
  BROWSER("spring.profiles.active"),
  DRIVER_TYPE("driver.type"),
  ENVIRONMENT("env.type"),
  PROFILES("spring.profiles.active"),

  // Web Run Info
  GRID_URL("grid.url"),
  SAUCE_URL("sauce.url"),
  SAUCE_LABS_TUNNEL_NAME("sauce.labs.tunnel"),
  MOBILE_USER_AGENT("mobile.user-agent"),
  IPHONE_USER_AGENT("iphone.user-agent"),

  // Sauce Labs Info,
  SAUCE_USER("sauce.user"),
  SAUCE_KEY("sauce.key"),

  // Appium Run Info
  APPIUM_URL("appium.url"),
  APPIUM_VERSION("appium.version"),
  DEVICE_ORIENTATION("device.orientation"),

  ANDROID_PLATFORM_NAME("android.platform.name"),
  ANDROID_PLATFORM_VERSION("android.platform.version"),
  ANDROID_DEVICE_NAME("android.device.name"),
  ANDROID_BROWSER("android.browser"),
  ANDROID_BROWSER_VERSION("android.browser.version"),
  ANDROID_RESET("android.reset"),
  ANDROID_NATIVE_WEBTAP("android.native.webtap"),

  IPHONE_PLATFORM_NAME("iphone.platform.name"),
  IPHONE_PLATFORM_VERSION("iphone.platform.version"),
  IPHONE_DEVICE_NAME("iphone.device.name"),
  IPHONE_X_DEVICE_NAME("iphone.x.device.name"),
  IPHONE_8_DEVICE_NAME("iphone.8.device.name"),
  IPHONE_BROWSER("iphone.browser"),

  ENV_TYPE("env.type"),
  BASE_URL("base.url"),
  
  // Time
  DEFAULT_AUTOMATION_WAIT("default.wait.in.seconds"),
  MAX_AUTOMATION_WAIT("max.wait.in.seconds"),
  ANIMATION_WAIT("animation.wait"),
  REACTION_WAIT("reaction.wait"),

  //
  ADMIN_EMAIL("admin.email"),
  ADMIN_PASSWORD("admin.password");

  String literal;
}
