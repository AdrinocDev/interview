package com.vodafone.calculator.service;

import com.vodafone.calculator.model.CalculateFibonacciResponse;
import com.vodafone.calculator.model.CalculateRandomResponse;
import com.vodafone.calculator.model.CalculateResponse;
import com.vodafone.calculator.model.CalculateSortResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorServiceTest {
  public static final List<Integer> inputList = List.of(1, 2, 3, 5);
  public static final List<Integer> fibonacciList = List.of(1, 3, 5, 2, 8);
  public static final String LOW_HIGH_SORT = "low_high";
  public static final String HIGH_LOW_SORT = "high_low";
  @InjectMocks private CalculatorService calculatorService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCalculateMissingNumber() {
    CalculateResponse response = calculatorService.calculateMissingNumber(inputList);

    assertEquals(response.getMissingNumber(), 4);
  }

  @Test
  void testCalculateMissingNumberWithSortLowHigh() {
    CalculateSortResponse response =
        calculatorService.calculateMissingNumberWithSort(inputList, LOW_HIGH_SORT);
    assertEquals(response.getMissingNumber(), 4);
    assertEquals(response.getList(), List.of(1, 2, 3, 5));
  }

  @Test
  void testCalculateMissingNumberWithSortHighLow() {
    CalculateSortResponse response =
        calculatorService.calculateMissingNumberWithSort(inputList, HIGH_LOW_SORT);
    assertEquals(response.getMissingNumber(), 4);
    assertEquals(response.getList(), List.of(5, 3, 2, 1));
  }

  @Test
  void testCalculateMissingNumberInRandomList() {
    int maxValue = 3;
    CalculateRandomResponse response =
        calculatorService.calculateMissingNumberInRandomList(maxValue);
    int maxValueInList = response.getList().stream().max(Comparator.naturalOrder()).get();
    assertTrue(response.getList().size() == maxValue - 1);
    assertTrue((maxValueInList == maxValue || maxValueInList == maxValue - 1));
  }

  @Test
  void testCalculateFibonacciList() {
    CalculateFibonacciResponse response = calculatorService.calculateFibonacciList(8);
    assertTrue(response.getFibonacciList().size() == fibonacciList.size() + 1);
    response.getFibonacciList().stream()
        .forEach(value -> assertTrue(fibonacciList.contains(value)));
  }
}
