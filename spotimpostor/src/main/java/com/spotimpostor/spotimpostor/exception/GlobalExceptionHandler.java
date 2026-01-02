package com.spotimpostor.spotimpostor.exception;

import com.spotimpostor.spotimpostor.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException e) {
    ApiResponse<Object> response = new ApiResponse<>(
            e.getMessage(),
            String.valueOf(e.getHttpStatus().value()),
            null
    );
    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

}
