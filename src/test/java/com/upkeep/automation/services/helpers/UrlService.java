package com.upkeep.automation.services.helpers;

import com.upkeep.automation.components.TestData;
import com.upkeep.automation.components.TestProperties;

import com.upkeep.automation.config.DataKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UrlService {

  private final TestProperties testProperties;
  private final String baseUrl;
  private final TestData testData;
  
  @Autowired
  public UrlService(
      TestProperties testProperties,
      TestData testData) {
    this.testProperties = testProperties;
    this.testData = testData;

    baseUrl = testProperties.getBaseUrl();
  }

  private String getQueryParams() {
    Map<String, String> queryParams = (Map<String, String>)
        testData.get(DataKeys.BROWSER_DATA_QUERY_PARAMS.getLiteral());

    String searchString = "";
    if (queryParams.size() > 0) {
      searchString = "?" + queryParams.entrySet()
                                      .stream()
                                      .map((entry) -> entry.getKey() + "=" + entry.getValue())
                                      .collect(Collectors.joining("&"));
    }

    return searchString;
  }
}
