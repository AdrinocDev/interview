package com.vodafone.calculator.service;

import com.vodafone.calculator.model.CalculateFibonacciResponse;
import com.vodafone.calculator.model.CalculateRandomResponse;
import com.vodafone.calculator.model.CalculateResponse;
import com.vodafone.calculator.model.CalculateSortResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class CalculatorService {

  public CalculateResponse calculateMissingNumber(List<Integer> list) {
    //Collections.sort(list);
    int missingNumber = 1;
    int n = list.size();
    int summation = 0;
    int sumTotal = ((n + 1) * (n + 2)) / 2;

    for (int i = 0; i < n; i++) {
      summation = summation + list.get(i);
    }
    missingNumber = Math.abs(sumTotal - summation);
    // TODO should be the list added to the history sorted or should be unsorted?
    return CalculateResponse.builder().missingNumber(missingNumber).list(list).build();
  }

  public CalculateSortResponse calculateMissingNumberWithSort(List<Integer> list, String sort) {
    List<Integer> sortedList = new ArrayList<>();
    sortedList.addAll(list);
    if ("low_high".equals(sort)) {
      Collections.sort(sortedList);
    } else if ("high_low".equals(sort)) {
      Collections.sort(sortedList, Collections.reverseOrder());
    }
    CalculateResponse missingNumber = calculateMissingNumber(list);

    return CalculateSortResponse.builder()
        .missingNumber(missingNumber.getMissingNumber())
        .list(sortedList)
        .build();
  }

  public CalculateRandomResponse calculateMissingNumberInRandomList(int maxValue) {
    List<Integer> randomList = generateRandomList(maxValue);
    CalculateResponse missingNumber = calculateMissingNumber(randomList);
    return CalculateRandomResponse.builder()
        .missingNumber(missingNumber.getMissingNumber())
        .list(randomList)
        .build();
  }

  public CalculateFibonacciResponse calculateFibonacciList(int value) {
    List<Integer> fibonacciList = generateFibonacciList(value);
    CalculateResponse missingNumber = calculateMissingNumber(fibonacciList);
    int size = fibonacciList.size();
    if (isEvenNumber(fibonacciList.size())) {
      fibonacciList.add(missingNumber.getMissingNumber());
    } else {
      fibonacciList.add(size, missingNumber.getMissingNumber());
    }
    return CalculateFibonacciResponse.builder().fibonacciList(fibonacciList).build();
  }

  private List<Integer> generateRandomList(int maxValue) {
    List<Integer> randomList = new ArrayList<>();
    for (int i = 1; i <= maxValue; i++) {
      randomList.add(i);
    }
    randomizerList(randomList);
    randomList.remove(new Random().nextInt(maxValue));
    return randomList;
  }

  private List<Integer> generateFibonacciList(int value) {
    List<Integer> fibonacciList = new ArrayList<>();
    int num1 = 0, num2 = 1;
    boolean fibNumberFound = false;
    while(!fibNumberFound && value >= num1){ //TODO if the sequence can´t be greater than the value, use num2 instead of num1
      int num3 = num1 + num2;
      num1 = num2;
      num2 = num3;
      fibonacciList.add(num1);
      fibNumberFound = fibonacciList.contains(value);
    }

    randomizerList(fibonacciList);
    return fibonacciList;
  }

  private void randomizerList ( List<Integer> list){
     Collections.shuffle(list);
  }

  private boolean isEvenNumber(int number) {
    return (number % 2) == 0;
  }
}
