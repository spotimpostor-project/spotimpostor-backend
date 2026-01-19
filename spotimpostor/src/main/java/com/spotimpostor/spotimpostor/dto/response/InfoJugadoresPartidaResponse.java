package com.spotimpostor.spotimpostor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoJugadoresPartidaResponse {
  private String jugador;
  private String rol;
  private String palabra;
}
