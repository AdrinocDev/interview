package com.vodafone.calculator.service;

import com.vodafone.calculator.model.CalculateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationHistoryServiceTest {

  public static final List<Integer> calcHistory1 = List.of(1, 2, 3, 5);

  public static final List<Integer> calcHistory2 = List.of(4, 1, 5, 6, 3);

  @InjectMocks private CalculationHistoryService calculationHistoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddToHistoryOrder1() {
    calculationHistoryService.addToHistory(mockCalculateResponse(calcHistory1, 4));
    assertEquals(calculationHistoryService.getCalculations(1).get(0).getList(), calcHistory1);
  }

  @Test
  void testAddToHistoryOrder2() {
    calculationHistoryService.addToHistory(mockCalculateResponse(calcHistory1, 4));
    calculationHistoryService.addToHistory(mockCalculateResponse(calcHistory2, 2));
    assertEquals(calculationHistoryService.getCalculations(2).get(0).getList(), calcHistory1);
    assertEquals(calculationHistoryService.getCalculations(2).get(1).getList(), calcHistory2);
  }

  private CalculateResponse mockCalculateResponse(List<Integer> calcHistory, int missingNumber) {
    return CalculateResponse.builder().missingNumber(missingNumber).list(calcHistory).build();
  }
}
