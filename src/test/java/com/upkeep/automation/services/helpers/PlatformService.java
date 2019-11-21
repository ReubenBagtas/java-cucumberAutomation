package com.upkeep.automation.services.helpers;

import com.upkeep.automation.config.Platform;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
public class PlatformService {

  private boolean isDesktop;
  private boolean isMobile;
  private boolean isSimulator;

  
  public PlatformService(Environment environment) {
    log.debug(environment.getActiveProfiles().toString());
    isDesktop =
            Arrays.stream(environment.getActiveProfiles())
                    .anyMatch(profile -> profile.endsWith(Platform.DESKTOP.getName()));

    isMobile =
            Arrays.stream(environment.getActiveProfiles())
                    .anyMatch(profile -> profile.endsWith(Platform.MOBILE.getName()));

    isSimulator =
            Arrays.stream(environment.getActiveProfiles())
                    .anyMatch(profile -> profile.endsWith(Platform.SIMULATOR.getName()));

    if (!isDesktop && !isMobile && !isSimulator) {
      throw new IllegalArgumentException("spring.profiles.active does not end with (desktop|mobile) !!!!");
    }
  }
}

