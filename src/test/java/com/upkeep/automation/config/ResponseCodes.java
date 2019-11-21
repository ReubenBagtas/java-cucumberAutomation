package com.upkeep.automation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodes {
  // Success
  OK(200),
  CREATED(201),
  ACCEPTED(202),
  NO_CONTENT(204),

  // Redirect
  MOVED_PERMANENTLY(301),
  FOUND(302),
  NOT_MODIFIED(304),

  // Client Errors
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  PAYMENT_REQUIRED(402),
  FORBIDDEN(403),
  NOT_FOUND(404),

  // Server Errors
  INTERNAL_SERVER_ERROR(500),
  NOT_IMPLEMENTED(501),
  BAD_GATEWAY(502),
  SERVICE_UNAVAILABLE(503),
  GATEWAY_TIMEOUT(504);

  int literal;
}
