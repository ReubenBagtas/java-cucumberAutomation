package com.upkeep.automation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Platform {

  DESKTOP("desktop"),
  MOBILE("mobile"),
  SIMULATOR("sim.mobile"),;

  private String name;
}
