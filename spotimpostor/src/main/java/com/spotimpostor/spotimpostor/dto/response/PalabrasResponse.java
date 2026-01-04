package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PalabrasResponse {
  private String palabra;
  private String pista;
}
