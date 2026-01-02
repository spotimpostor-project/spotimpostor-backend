package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUsuarioRequest {

  @NotBlank(message = "Nombre de usuario obligatorio")
  private String nombre;
  @NotBlank(message = "Correo electronico obligatorio")
  private String correo;
  @NotBlank(message = "Contraseña obligatoria")
  private String password;
}
