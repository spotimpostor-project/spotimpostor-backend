package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.request.CambiarPasswordRequest;
import com.spotimpostor.spotimpostor.dto.request.CambiarUsernameRequest;
import com.spotimpostor.spotimpostor.dto.request.CreateUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.request.ConsultarUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoUsuarioResponse;
import com.spotimpostor.spotimpostor.service.UsuarioService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<ApiResponse<InfoUsuarioResponse>> registrar(
    @Valid @RequestBody CreateUsuarioRequest createUsuarioRequest
  ) {
    InfoUsuarioResponse infoUsuarioResponse = usuarioService.registerUser(createUsuarioRequest);
    return ResponseEntity.ok(new ApiResponse<>("Se registró el usuario exitosamente", "200", infoUsuarioResponse));
    //return new ResponseEntity<>(infoUsuarioResponse, HttpStatus.CREATED);
  }

  @GetMapping("/validar")
  public ResponseEntity<ApiResponse<Boolean>> validar(
    @Valid @RequestBody ConsultarUsuarioRequest consultarUsuarioRequest
  ) {
    Boolean valido = usuarioService.validarUser(consultarUsuarioRequest);
    return ResponseEntity.ok(new ApiResponse<>("Autenticación exitosa", "200", valido));
    //return new ResponseEntity<>(valido, HttpStatus.OK);
  }

  @PatchMapping("/nombre")
  public ResponseEntity<ApiResponse<InfoUsuarioResponse>> cambiarNombre(
    @Valid @RequestBody CambiarUsernameRequest cambiarUsernameRequest
  ) {
    InfoUsuarioResponse infoUsuarioResponse = usuarioService.updateNombre(cambiarUsernameRequest);
    return ResponseEntity.ok(new ApiResponse<>("Actualización exitosa", "200", infoUsuarioResponse));
  }

  @PatchMapping("/password")
  public ResponseEntity<ApiResponse<InfoUsuarioResponse>> cambiarPassword(
    @Valid @RequestBody CambiarPasswordRequest cambiarPasswordRequest
  ){
    InfoUsuarioResponse infoUsuarioResponse = usuarioService.updatePassword(cambiarPasswordRequest);
    return ResponseEntity.ok(new ApiResponse<>("Actualización exitosa", "200", infoUsuarioResponse));
  }
}
