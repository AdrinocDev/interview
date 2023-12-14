package com.vodafone.calculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateRequest {
  private List<Integer> list;
}
