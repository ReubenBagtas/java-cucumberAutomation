package com.upkeep.automation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataKeys {

  APIKEY("apikey"),
  APISECRET("apisecret"),

  BROWSER_DATA_QUERY_PARAMS("queryParams"),

  RESPONSE_BODY("rsBody"),
  RESPONSE_CONTENT_TYPE("rsContentType"),
  RESPONSE_COOKIES("rsCookies"),
  RESPONSE_HEADERS("rsHeaders"),
  RESPONSE_OBJECT("rsObj"),
  RESPONSE_STATUS("rsStatus");

  String literal;
}
