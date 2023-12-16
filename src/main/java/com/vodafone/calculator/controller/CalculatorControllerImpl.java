package com.vodafone.calculator.controller;

import com.vodafone.calculator.model.*;
import com.vodafone.calculator.service.CalculationHistoryService;
import com.vodafone.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculatorControllerImpl implements CalculatorApi {

  private final CalculatorService calculatorService;

  private final CalculationHistoryService historyService;

  @Override
  public ResponseEntity<CalculateResponse> calculateMissingNumber(CalculateRequest request) {
    CalculateResponse calculateResponse =
        calculatorService.calculateMissingNumber(request.getList());
    historyService.addToHistory(calculateResponse);
    return ResponseEntity.status(HttpStatus.OK).body(calculateResponse);
  }

  @Override
  public ResponseEntity<CalculateSortResponse> calculateSort(CalculateSortRequest request) {;
    return ResponseEntity.status(HttpStatus.OK).body(calculatorService.calculateMissingNumberWithSort(request.getList(), request.getSort()));
  }

  @Override
  public ResponseEntity<CalculateRandomResponse> calculateRandom(CalculateRandomRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(calculatorService.calculateMissingNumberInRandomList(request.getMaxValue()));
  }

  @Override
  public ResponseEntity<CalculateFibonacciResponse> calculateFibonacci(CalculateFibonacciRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(calculatorService.calculateFibonacciList(request.getValue()));
  }

  @Override
  public ResponseEntity<List<CalculationHistoryResponse>> missingNumbers(CalculationHistoryRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(historyService.getCalculations(request.getLastCalculations()));
  }
}
