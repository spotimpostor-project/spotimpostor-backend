package com.spotimpostor.spotimpostor.dto.request;

import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateColeccionRequest {

  @NotBlank(message = "Nombre de la colección obligatoria")
  private String nombre;
  @NotNull(message = "Tipo de colección obligatorio")
  private TipoColeccion visibilidad;

  @NotEmpty(message = "La colección debe tener al menos una palabra")
  @Valid
  private List<PalabraDTO> palabras;
}
