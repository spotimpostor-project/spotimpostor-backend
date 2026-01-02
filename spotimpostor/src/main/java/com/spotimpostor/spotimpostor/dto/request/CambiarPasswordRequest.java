package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambiarPasswordRequest {
  @NotBlank(message = "Contraseña obligatoria")
  private String password;
  @NotBlank(message = "Direccion de correo obligatoria")
  private String correo;
}
