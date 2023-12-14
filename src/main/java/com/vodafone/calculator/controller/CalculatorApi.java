package com.vodafone.calculator.controller;

import com.vodafone.calculator.model.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.vodafone.calculator.utils.UrlConstants.*;

public interface CalculatorApi {
    @RequestMapping(value = CALCULATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<CalculateResponse> calculateMissingNumber(@RequestBody @Valid final CalculateRequest calculateRequest);

    @RequestMapping(value = CALCULATE_SORT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<CalculateSortResponse> calculateSort(@RequestBody @Valid final CalculateSortRequest calculateSortRequest);

    @RequestMapping(value = CALCULATE_RANDOM, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<CalculateRandomResponse> calculateRandom(@RequestBody @Valid final CalculateRandomRequest calculateRandomRequest);

    @RequestMapping(value = CALCULATE_FIBONACCI, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<CalculateFibonacciResponse> calculateFibonacci(@RequestBody @Valid final CalculateFibonacciRequest calculateFibonacciRequest);
}
