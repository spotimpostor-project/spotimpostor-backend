package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import com.spotimpostor.spotimpostor.dto.response.ModoPartidaResponse;
import org.springframework.stereotype.Component;

@Component
public class ModoPartidaMapper {

  public ModoPartidaResponse mapModoPartida(ModoPartida modoPartida) {
    return ModoPartidaResponse.builder()
            .modo(modoPartida.getModo())
            .descripcion(modoPartida.getDescripcion())
            .build();
  }
}
