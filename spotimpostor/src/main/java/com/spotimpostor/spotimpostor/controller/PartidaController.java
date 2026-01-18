package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.request.CreatePartidaRequest;
import com.spotimpostor.spotimpostor.dto.request.UpdatePartidaRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoJugadoresPartidaResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoPartidaResponse;
import com.spotimpostor.spotimpostor.service.PartidaService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
public class PartidaController {

  private final PartidaService partidaService;

  @PostMapping
  public ResponseEntity<ApiResponse<InfoPartidaResponse>> registrar (
    @Valid @RequestBody CreatePartidaRequest dtoRequest,
    Authentication authentication
  ) {
    String correo = (authentication != null) ? authentication.getName() : null;
    InfoPartidaResponse response = partidaService.registrarPartida(dtoRequest, correo);
    return ResponseEntity.ok(new ApiResponse<>("Registro exitoso", "200", response));
  }

  @PatchMapping
  public ResponseEntity<ApiResponse<Void>> update (
          @Valid @RequestBody UpdatePartidaRequest dtoRequest
  ) {
    partidaService.actualizarPartida(dtoRequest);
    return ResponseEntity.ok(new ApiResponse<>("Actualización exitosa", "200", null));
  }
}
