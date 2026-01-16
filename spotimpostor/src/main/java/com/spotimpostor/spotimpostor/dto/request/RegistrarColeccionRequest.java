package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RegistrarColeccionRequest {
  @NotBlank(message = "Nombre de coleccion obligatorio")
  private String nombreColeccion;
  /*
  @Email
  @NotBlank(message = "Correo de usuario obligatorio")
  private String correoUsuario;
  */
  @NotEmpty(message = "La lista de palabras no puede estar vacía")
  private List<String> palabras;
}
