package com.spotimpostor.spotimpostor.dto.request;

import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatePartidaRequest {

  @NotBlank(message = "El modo de juego es obligatorio")
  private String modo;

  @NotBlank(message = "El nombre de la coleccion es obligatorio")
  private String nombreColeccion;

  //@NotBlank(message = "El codigo de la coleccion es obligatorio")
  private String codigoColeccion;

  @NotNull(message = "El tipo de la coleccion es obligatorio")
  private TipoColeccion tipoColeccion;

  @NotNull(message = "La cantidad de jugadores debe especificarse")
  @Positive(message = "Debe ser una cantidad positiva")
  private Integer cantidadJugadores;

  @NotNull(message = "La cantidad de impostores debe especificarse")
  @Positive(message = "Debe ser una cantidad positiva")
  private Integer cantidadImpostores;

  @Email
  //@NotBlank(message = "Correo de usuario obligatorio")
  private String correo;

  @NotEmpty(message = "Lista de jugadores obligatoria")
  private List<String> jugadores;
}
