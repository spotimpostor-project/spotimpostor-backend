package com.spotimpostor.spotimpostor.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InfoColeccionPublicaResponse {
  private String nombre;
  private String codigo;
  private String creador;
  private List<String> palabras;
}
