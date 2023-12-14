package com.vodafone.calculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateSortRequest {
  private List<Integer> list;
  private String sort;
}
