package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.dto.request.PalabraDTO;

public class PalabraMapper {
  public static PalabraDTO mapPalabraDTO(Palabra palabra) {
    return PalabraDTO.builder()
            .id(palabra.getId())
            .palabra(palabra.getPalabra())
            .build();
  }

  public static Palabra buildPalabra(String palabraDTO, Coleccion coleccion) {
    Palabra palabra = new Palabra();
    palabra.setPalabra(palabraDTO);
    palabra.setColeccion(coleccion);
    return palabra;
  }
}
