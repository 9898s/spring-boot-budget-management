package com.example.budget.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

  private final boolean status;
  private final String message;
  private final T data;
}
