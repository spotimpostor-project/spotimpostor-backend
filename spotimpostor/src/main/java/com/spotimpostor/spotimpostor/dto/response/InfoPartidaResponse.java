package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class InfoPartidaResponse {
  private UUID idPartida;
  private List<InfoJugadoresPartidaResponse> rolesJugadores;
}
