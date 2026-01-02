package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambiarUsernameRequest {
  @NotBlank(message = "Nombre de usuario obligatorio")
  private String nombre;
  @NotBlank(message = "Direccion de correo obligatoria")
  private String correo;
}
