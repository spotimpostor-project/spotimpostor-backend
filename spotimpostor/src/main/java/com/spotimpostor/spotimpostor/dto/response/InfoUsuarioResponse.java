package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoUsuarioResponse {
  private String nombre;
  private String correo;
}
