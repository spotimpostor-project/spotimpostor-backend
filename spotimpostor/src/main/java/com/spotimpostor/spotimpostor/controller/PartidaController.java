package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.request.CreatePartidaRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoPartidaResponse;
import com.spotimpostor.spotimpostor.dto.response.PalabrasResponse;
import com.spotimpostor.spotimpostor.service.PartidaService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
  public ResponseEntity<ApiResponse<List<InfoPartidaResponse>>> registrar (
    @Valid @RequestBody CreatePartidaRequest dtoRequest,
    Authentication authentication
  ) {
    String correo = (authentication != null) ? authentication.getName() : null;
    List<InfoPartidaResponse> partidaResponseList = partidaService.registrarPartida(dtoRequest, correo);
    return ResponseEntity.ok(new ApiResponse<>("Registro exitoso", "200", partidaResponseList));
  }

}
