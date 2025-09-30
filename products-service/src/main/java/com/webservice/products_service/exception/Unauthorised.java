package com.webservice.products_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Unauthorised extends  RuntimeException {
  private final String message;
}
