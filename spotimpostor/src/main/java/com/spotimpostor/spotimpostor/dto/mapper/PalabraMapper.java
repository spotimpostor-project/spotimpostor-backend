package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;

public class PalabraMapper {
  public static Palabra buildPalabra(String palabraDTO, Coleccion coleccion) {
    Palabra palabra = new Palabra();
    palabra.setPalabra(palabraDTO);
    palabra.setColeccion(coleccion);
    return palabra;
  }
}
