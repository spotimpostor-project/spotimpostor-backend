package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class UpdatePartidaRequest {
  @NotNull(message = "ID de partida obligatorio")
  private UUID idPartida;
  @NotNull(message = "Duración de partida obligatoria")
  private LocalTime duracion;
  @NotNull(message = "Resultado de partida obligatorio")
  private Boolean esVictoria;
}
