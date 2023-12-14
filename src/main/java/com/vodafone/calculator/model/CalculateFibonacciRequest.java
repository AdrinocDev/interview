package com.vodafone.calculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateFibonacciRequest {
  private int value;
}
