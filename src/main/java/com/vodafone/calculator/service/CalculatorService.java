package com.vodafone.calculator.service;

import com.vodafone.calculator.model.CalculateFibonacciResponse;
import com.vodafone.calculator.model.CalculateRandomResponse;
import com.vodafone.calculator.model.CalculateResponse;
import com.vodafone.calculator.model.CalculateSortResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculatorService {

  /**
   * Calculate the missing number in a list
   *
   * @param list
   * @return missing number
   */
  public CalculateResponse calculateMissingNumber(List<Integer> list) {
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

  /**
   * Calculate the missing number in an unordered list and return both the missing number and the
   * sorted list
   *
   * @param list
   * @param sort
   * @return missing number and its sorted list
   */
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

  /**
   * Calculates the result of the missing value in a random list that it will return.
   *
   * @param maxValue
   * @return missing number and its random list
   */
  public CalculateRandomResponse calculateMissingNumberInRandomList(int maxValue) {
    List<Integer> randomList = generateRandomList(maxValue);
    CalculateResponse missingNumber = calculateMissingNumber(randomList);
    return CalculateRandomResponse.builder()
        .missingNumber(missingNumber.getMissingNumber())
        .list(randomList)
        .build();
  }

  /**
   * Calculate the Fibonacci sequence up to the given number and return that sequence with random
   * order but with the given number in the center, in case an even length it must be in the
   * furthest one.
   *
   * <p>If the value is not part of the Fibonacci sequence, calculate the next one inside the
   * sequence. i.e: value = 6 return Fibonacci 8.
   *
   * @param value
   * @return fibonachi_list
   */
  public CalculateFibonacciResponse calculateFibonacciList(int value) {
    List<Integer> fibonacciList = generateFibonacciList(value);
    int size = fibonacciList.size();
    // Checking that the number is part of the sequence
    int greaterValue = fibonacciList.contains(value) ? value : (getGreaterValue(fibonacciList));
    if (isEvenNumber(fibonacciList.size())) {
      fibonacciList.remove(Integer.valueOf(greaterValue));
      fibonacciList.add(greaterValue);
    } else {
      fibonacciList.remove(Integer.valueOf(greaterValue));
      fibonacciList.add((size / 2), greaterValue);
    }
    return CalculateFibonacciResponse.builder().fibonacciList(fibonacciList).build();
  }

  /**
   * Generate a random list given a max. value
   *
   * @param maxValue
   * @return random list
   */
  private List<Integer> generateRandomList(int maxValue) {
    List<Integer> randomList = new ArrayList<>();
    for (int i = 1; i <= maxValue; i++) {
      randomList.add(i);
    }
    Collections.shuffle(randomList);
    randomList.remove(new Random().nextInt(maxValue));
    return randomList;
  }

  /**
   * Generate the fibonacci list of a given value. If the value is not part of the sequence, return
   * the fibonacci list of the next
   *
   * @param value
   * @return fibonacci list
   */
  private List<Integer> generateFibonacciList(int value) {
    List<Integer> fibonacciList = new ArrayList<>();
    int num1 = 0, num2 = 1;
    boolean fibNumberFound = false;
    while (!fibNumberFound
        && value
            >= num1) { // TODO if the sequence can´t be greater than the value, use num2 instead of
      // num1
      int num3 = num1 + num2;
      num1 = num2;
      num2 = num3;
      fibonacciList.add(num1);
      fibNumberFound = fibonacciList.contains(value);
    }

    Collections.shuffle(fibonacciList);
    return fibonacciList;
  }

  /**
   * Return greater value in a list
   *
   * @param list
   * @return greater value
   */
  private int getGreaterValue(List<Integer> list) {
    return list.stream().max(Comparator.naturalOrder()).get();
  }

  /**
   * Return true if the given number is even
   *
   * @param number
   * @return whether is even number
   */
  private boolean isEvenNumber(int number) {
    return (number % 2) == 0;
  }
}
