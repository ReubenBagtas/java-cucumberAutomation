package com.upkeep.automation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverType {

  LOCAL("local"),
  GRID("grid"),
  APPIUM("appium"),
  SAUCE_LABS("sauce");

  private String name;

}
