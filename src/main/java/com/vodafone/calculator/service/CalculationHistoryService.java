package com.vodafone.calculator.service;

import com.vodafone.calculator.model.CalculateResponse;
import com.vodafone.calculator.model.CalculationHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculationHistoryService {
  private List<CalculationHistoryResponse> calculationHistoryList;

  CalculationHistoryService() {
    calculationHistoryList = new ArrayList<>();
  }

  public void addToHistory(CalculateResponse calculation) {
    calculationHistoryList.add(
        CalculationHistoryResponse.builder()
            .order(calculationHistoryList.size() + 1)
            .missingNumber(calculation.getMissingNumber())
            .list(calculation.getList())
            .build());
  }

  public List<CalculationHistoryResponse> getCalculations(int order) {
    return calculationHistoryList.subList(0, order);
  }
}
