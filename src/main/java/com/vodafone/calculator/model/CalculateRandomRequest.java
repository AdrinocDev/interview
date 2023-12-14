package com.vodafone.calculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateRandomRequest {
  private int maxValue;
}
