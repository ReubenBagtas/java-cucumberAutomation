package com.upkeep.automation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("environment/common/main.properties"),
    @PropertySource("environment/${env.type}/main.properties")
    })
@ComponentScan(basePackages = {
    "com.upkeep.automation.components",
    "com.upkeep.automation.config",
    "com.upkeep.automation.selenium",
    "com.upkeep.automation.services"
    })
public class TestConfig {

}
