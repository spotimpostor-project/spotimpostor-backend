package com.spotimpostor.spotimpostor.dto.request;

import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarVisibilidadRequest {
  @NotBlank(message = "Codigo de coleccion obligatorio")
  String codigo;
  @NotNull(message = "Tipo de coleccion obligatorio")
  TipoColeccion tipoColeccion;
}
