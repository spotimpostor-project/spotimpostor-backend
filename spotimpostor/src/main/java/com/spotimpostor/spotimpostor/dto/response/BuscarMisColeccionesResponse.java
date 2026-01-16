package com.spotimpostor.spotimpostor.dto.response;

import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuscarMisColeccionesResponse {
  private String nombre;
  private String codigo;
  private TipoColeccion visibilidad;
}
