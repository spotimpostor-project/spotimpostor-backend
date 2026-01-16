package com.spotimpostor.spotimpostor.exception;

import com.spotimpostor.spotimpostor.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
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

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<String>> handleBadCredentials(BadCredentialsException ex) {
    // Aquí defines el mensaje exacto que vería el detective
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse<>("Credenciales inválidas, intenta de nuevo", "401", null));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ApiResponse<>("Acceso denegado o sesión expirada.", "403", null));
  }
}
