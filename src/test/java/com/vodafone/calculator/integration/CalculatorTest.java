package com.vodafone.calculator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.calculator.CalculatorApplication;
import com.vodafone.calculator.controller.CalculatorControllerImpl;
import com.vodafone.calculator.model.*;
import com.vodafone.calculator.service.CalculationHistoryService;
import com.vodafone.calculator.service.CalculatorService;
import com.vodafone.calculator.utils.UrlConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CalculatorApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class CalculatorTest {

  public static final List<Integer> inputList1 = List.of(1, 2, 3, 5);

  public static final List<Integer> inputList2 = List.of(5, 3, 2, 1);

  public static final List<Integer> inputList3 = List.of(4, 5, 2, 3, 7, 1);

  public static final List<Integer> fibonacciList = List.of(1, 3, 5, 2, 8);

  public static final String LOW_HIGH_SORT = "low_high";

  public static final String HIGH_LOW_SORT = "high_low";
  @Autowired private MockMvc mockMvc;

  @Mock private CalculatorService calculatorService;

  @Mock private CalculationHistoryService historyService;

  @InjectMocks private CalculatorControllerImpl calculatorController;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testCalculateMissingNumber() throws Exception {
    CalculateRequest request = mockCalculateRequest(inputList1);
    CalculateResponse response = mockCalculateResponse(inputList1, 4);

    when(calculatorService.calculateMissingNumber(any())).thenReturn(response);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.CALCULATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void testCalculateLowHighSort() throws Exception {
    CalculateSortRequest request = mockCalculateSortRequest(inputList1, LOW_HIGH_SORT);

    CalculateSortResponse response = mockCalculateSortResponse(inputList1, 4);

    when(calculatorService.calculateMissingNumberWithSort(any(), any())).thenReturn(response);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.CALCULATE_SORT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void testCalculateHighLowSort() throws Exception {
    CalculateSortRequest request = mockCalculateSortRequest(inputList2, HIGH_LOW_SORT);

    CalculateSortResponse response = mockCalculateSortResponse(inputList2, 4);

    when(calculatorService.calculateMissingNumberWithSort(any(), any())).thenReturn(response);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.CALCULATE_SORT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void testCalculateRandom() throws Exception {
    CalculateRandomRequest request = mockCalculateRandomRequest(5);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.CALCULATE_RANDOM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.list.size()").value(4));
  }

  @Test
  void testCalculateFibonacci() throws Exception {
    CalculateFibonacciRequest request = mockCalculateFibRequest(8);
    CalculateFibonacciResponse response = mockCalculateFibResponse(fibonacciList);

    when(calculatorService.calculateFibonacciList(anyInt())).thenReturn(response);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.CALCULATE_FIBONACCI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Fibonachi_List.size()").value(fibonacciList.size() + 1));
  }

  @Test
  void testMissingNumbers() throws Exception {
    CalculationHistoryRequest request = mockCalculationHistoryRequest(2);
    CalculateRequest calculateRequest1 = mockCalculateRequest(inputList1);
    CalculateResponse calculateResponse1 = mockCalculateResponse(inputList1, 4);
    CalculateRequest calculateRequest2 = mockCalculateRequest(inputList3);
    CalculateResponse calculateResponse2 = mockCalculateResponse(inputList3, 6);

    List<CalculationHistoryResponse> response =
        List.of(
            mockCalculationHistoryResponse(1, mockCalculateResponse(inputList1, 4)),
            mockCalculationHistoryResponse(2, mockCalculateResponse(inputList3, 6)));

    when(historyService.getCalculations(anyInt())).thenReturn(response);
    // adding first calculation
    addCalculation(calculateRequest1, calculateResponse1);
    // adding second calculation
    addCalculation(calculateRequest2, calculateResponse2);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(UrlConstants.MISSING_NUMBERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(
            print()) // This print is for ease of testing. All other prints have been removed to
                     // avoid noise.
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  private CalculateRequest mockCalculateRequest(List<Integer> list) {
    return CalculateRequest.builder().list(list).build();
  }

  private CalculateResponse mockCalculateResponse(List<Integer> list, int missingNumber) {
    return CalculateResponse.builder().missingNumber(missingNumber).list(list).build();
  }

  private CalculateSortRequest mockCalculateSortRequest(List<Integer> list, String sort) {
    return CalculateSortRequest.builder().list(list).sort(sort).build();
  }

  private CalculateSortResponse mockCalculateSortResponse(List<Integer> list, int missingNumber) {
    return CalculateSortResponse.builder().missingNumber(missingNumber).list(list).build();
  }

  private CalculateRandomRequest mockCalculateRandomRequest(int maxValue) {
    return CalculateRandomRequest.builder().maxValue(maxValue).build();
  }

  private CalculateFibonacciRequest mockCalculateFibRequest(int value) {
    return CalculateFibonacciRequest.builder().value(value).build();
  }

  private CalculateFibonacciResponse mockCalculateFibResponse(List<Integer> list) {
    return CalculateFibonacciResponse.builder().fibonacciList(list).build();
  }

  private CalculationHistoryRequest mockCalculationHistoryRequest(int order) {
    return CalculationHistoryRequest.builder().lastCalculations(order).build();
  }

  private CalculationHistoryResponse mockCalculationHistoryResponse(
      int order, CalculateResponse calculateResponse) {
    return CalculationHistoryResponse.builder()
        .order(order)
        .missingNumber(calculateResponse.getMissingNumber())
        .list(calculateResponse.getList())
        .build();
  }

  private void addCalculation(CalculateRequest request, CalculateResponse response)
      throws Exception {
    when(calculatorService.calculateMissingNumber(any())).thenReturn(response);
    mockMvc.perform(
        MockMvcRequestBuilders.get(UrlConstants.CALCULATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
  }
}
