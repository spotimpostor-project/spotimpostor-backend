package com.spotimpostor.spotimpostor.service;

import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import com.spotimpostor.spotimpostor.dto.mapper.ModoPartidaMapper;
import com.spotimpostor.spotimpostor.dto.response.ModoPartidaResponse;
import com.spotimpostor.spotimpostor.repository.ModoPartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModoPartidaService {

  private final ModoPartidaRepository modoPartidaRepository;
  private final ModoPartidaMapper mapper;

  @Transactional
  public List<ModoPartidaResponse> consultarModoPartida() {
    List<ModoPartida> modosPartidas = modoPartidaRepository.findAll();
    return modosPartidas.stream().map(mapper::mapModoPartida).collect(Collectors.toList());
  }

}
