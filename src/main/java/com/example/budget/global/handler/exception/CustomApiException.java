package com.example.budget.global.handler.exception;

public class CustomApiException extends RuntimeException {

  public CustomApiException(String message) {
    super(message);
  }
}
