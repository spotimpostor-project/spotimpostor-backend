package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import com.spotimpostor.spotimpostor.domain.entity.Partida;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.dto.request.CreatePartidaRequest;

import java.time.LocalDate;

public class PartidaMapper {
  public static Partida buildPartida (CreatePartidaRequest dto, Usuario usuario, Coleccion coleccion, ModoPartida modo) {
    return Partida.builder()
            .usuario(usuario)
            .coleccion(coleccion)
            .modoPartida(modo)
            .cantidadImpostores(dto.getCantidadImpostores())
            .cantidadJugadores(dto.getCantidadJugadores())
            .fechaCreacion(LocalDate.now())
            .build();
  }
}
