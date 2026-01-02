package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.response.ModoPartidaResponse;
import com.spotimpostor.spotimpostor.service.ModoPartidaService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modos_partida")
@RequiredArgsConstructor
public class ModoPartidaController {

  private final ModoPartidaService modoPartidaService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<ModoPartidaResponse>>> consultarTodos() {
    List<ModoPartidaResponse> modoPartidaList = modoPartidaService.consultarModoPartida();
    return ResponseEntity.ok(new ApiResponse<>("Se consultó exitosamente", "200", modoPartidaList));
  }
}
