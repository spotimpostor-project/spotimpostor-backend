package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusquedaColeccionRequest {
  private String query;
  @NotBlank(message = "Tipo de búsqueda obligatorio")
  private String tipoBusqueda;
  private String tipoOrden;
  private String codigo;
}
