package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConsultarUsuarioRequest {
  @NotBlank(message = "Direccion de correo obligatoria")
  private String correo;
  @NotBlank(message = "Contraseña obligatoria")
  private String password;
}
