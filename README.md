## Automation Test Suite

### Pre - requisites

  * Docker
  * Maven
  * JDK 1.8
  * Lombok Plugin(for intellij)
  * [Allure](https://docs.qameta.io/allure)

### Setup Env variables
```bash
export SELENIUM_CHROME_DRIVER="/path/to/chromedriver"
export SELENIUM_GECKO_DRIVER="/path/to/geckodriver"

```

### Running the tests
Manually execute something similar to
```bash
mvn clean verify
```

### Useful maven switches

  * `-Dbase.url=` Examples: http://localhost:8080
  * `-Dspring.profiles.active=` Default is: chrome.desktop (drivers located in src/test/java/.../config/drivers)
  * `-Denv.type=` Examples: qa or stage or prod
  * `-Dbrowser.instances=` modify the number of browser ie feature files run in parallel, default is: 2
  
E.g.
```bash
mvn clean install -Ddriver.type=local -Dspring.profiles.active="chrome.desktop" -Denv.type=qa -Dbrowser.instances=1
```

### Allure Reports
##### Command to generate reports
  * `mvn clean install -Ddriver.type=local -Dspring.profiles.active="chrome.desktop" -Denv.type=qa -Dbrowser.instances=1`

*Results directory: target/site/allure-maven-plugin/index.html*
##### No reports generated
  * `mvn clean test -Ddriver.type=local -Dspring.profiles.active="chrome.desktop" -Denv.type=qa -Dbrowser.instances=1`

### Intellij Run Config Setup

##### Step to Cucumber Runner
  1. Open: Run -> Edit Configurations...
  2. Click Templates dropdown in the left nav
  3. Select Cucumber Java (If missing, download the plugin)

##### Info for Cucumber Java Runner
  * `Main Class` io.cucumber.core.cli.Main
  * `Glue` com.upkeep.automation
  * `VM Options` 
  ```bash
  -Dwebdriver.chrome.driver=/Users/davidconard/desktop/chromedriver
  -Dspring.profiles.active=chrome.desktop
  -Denv.type=qa
  -Dbrowser.instances=1
  ```
  * `Working Directory` path/to/project/root
  * `Use classpath of module` upkeep-automation
# java-cucumberAutomation
