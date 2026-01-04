package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoPartidaResponse {
  private String jugador;
  private String rol;
  private String palabra;
}
