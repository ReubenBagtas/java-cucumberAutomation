package com.upkeep.automation.services.helpers;

import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.upkeep.automation.components.TestData;
import com.upkeep.automation.components.TestProperties;

import com.upkeep.automation.config.DataKeys;
import com.upkeep.automation.services.helpers.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class RestService {

  private final JsonService jsonService;
  private final TestData testData;
  private final TestProperties testProperties;
  
  @Autowired
  public RestService(
      JsonService jsonService,
      TestData testData,
      TestProperties testProperties) {
    this.jsonService = jsonService;
    this.testData = testData;
    this.testProperties = testProperties;
  }

  public Response get(String endpoint, Map<String, String> headers) throws IOException {
    return given().with().headers(headers)
                  .then().get(endpoint, new Object[0]);
  }

  public Response delete(String endpoint, Map<String, String> headers) throws IOException {
    return given().with().headers(headers)
                  .then().delete(endpoint, new Object[0]);
  }

  public Response post(String endpoint, Map<String, String> headers, Map<String, Object> body) throws IOException {
    return given().with().headers(headers)
                  .when().body(body)
                  .then().post(endpoint, new Object[0]);
  }

  public Response put(String endpoint, Map<String, String> headers, Map<String, Object> body) throws IOException {
    return given().with().headers(headers)
                  .when().body(body)
                  .then().put(endpoint, new Object[0]);
  }

  public void verifyStatusCodeIs(int code) {
    assertThat(code).isEqualTo(testData.get(DataKeys.RESPONSE_STATUS.getLiteral()));
  }

  public void verifyContentTypeIs(String contentType) {
    String actual = String.valueOf(testData.get(DataKeys.RESPONSE_CONTENT_TYPE.getLiteral()));
    assertThat(actual).containsIgnoringCase(contentType);
  }

  public void verifyHeadersPresent(List<String> headerKeys) {
    List<String> missingKeys = new ArrayList<>();

    for (String h : headerKeys) {
      Headers actualHeaders = getHeaders((Response) testData.get(DataKeys.RESPONSE_BODY.getLiteral()));

      try {
        assertThat(actualHeaders.hasHeaderWithName(h)).isTrue();
      } catch (AssertionError e) {
        missingKeys.add(h);
        System.out.print(h);
      }
    }

    if (missingKeys.size() > 0) {
      throw new AssertionError("Headers Missing From Response: " + missingKeys.toString());
    }
  }

  public void verifyHeaderMinMaxRange(String headerKey, int min, int max) {
    int actual = Integer.parseInt(getHeader((Response) testData.get(DataKeys.RESPONSE_BODY.getLiteral()), headerKey));

    assertThat(actual).isBetween(min, max);
  }

  private int getStatusCode(Response response) {
    return response.getStatusCode();
  }

  private String getContentType(Response response) {
    return response.getContentType();
  }

  public String getCookie(Response response, String cookieKey) {
    return response.getCookie(cookieKey);
  }

  private Map<String, String> getCookies(Response response) {
    return response.getCookies();
  }

  public String getHeader(Response response, String headerKey) {
    return response.header(headerKey);
  }

  private Headers getHeaders(Response response) {
    return response.headers();
  }
}
