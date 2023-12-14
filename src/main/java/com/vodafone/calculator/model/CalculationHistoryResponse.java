package com.vodafone.calculator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CalculationHistoryResponse {
  private int order;
  private int missingNumber;
  private List<Integer> list;
}
