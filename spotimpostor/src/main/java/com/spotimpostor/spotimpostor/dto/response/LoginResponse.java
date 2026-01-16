package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  private String token;
  private String nombre;
}
