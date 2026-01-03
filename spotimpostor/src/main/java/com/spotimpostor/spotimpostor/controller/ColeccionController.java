package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.request.CambiarVisibilidadRequest;
import com.spotimpostor.spotimpostor.dto.request.RegistrarColeccionRequest;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.service.ColeccionService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

  private final ColeccionService coleccionService;

  @GetMapping("/general")
  public ResponseEntity<ApiResponse<List<String>>> consultarColeccionGeneral () {
    List<String> colecciones = coleccionService.findColeccionesGenerales();
    return ResponseEntity.ok(new ApiResponse<>("Consulta de colecciones exitosa", "200", colecciones));
  }

  @GetMapping("/comunidad")
  public ResponseEntity<ApiResponse<List<BuscarColeccionPublicaResponse>>> consultarColeccionPublica () {
    List<BuscarColeccionPublicaResponse> colecciones = coleccionService.findColeccionesPublicas();
    return ResponseEntity.ok(new ApiResponse<>("Consulta de colecciones exitosa", "200", colecciones));
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<ApiResponse<InfoColeccionPublicaResponse>> consultarDetalleColeccionUsuario(
    @PathVariable String codigo
  ) {
    InfoColeccionPublicaResponse coleccionPublicaResponse = coleccionService.getDetalleColeccionUsuario(codigo);
    return ResponseEntity.ok(new ApiResponse<>("Consulta de detalles exitosa", "200", coleccionPublicaResponse));
  }

  //TODO: Si esta privado no ver detalles

  @PostMapping
  public ResponseEntity<ApiResponse<InfoColeccionPublicaResponse>> registrar(
    @Valid @RequestBody RegistrarColeccionRequest dtoRequest
  ) {
    InfoColeccionPublicaResponse coleccionPublicaResponse = coleccionService.registerColeccion(dtoRequest);
    return ResponseEntity.ok(new ApiResponse<>("Registro exitoso", "200", coleccionPublicaResponse));
  }

  @PatchMapping("/tipo")
  public ResponseEntity<ApiResponse<InfoColeccionPublicaResponse>> actualizarVisibilidad (
    @Valid @RequestBody CambiarVisibilidadRequest dtoRequest
  ) {
    InfoColeccionPublicaResponse coleccionPublicaResponse = coleccionService.updateVisibilidad(dtoRequest);
    return ResponseEntity.ok(new ApiResponse<>("Actualizacion exitosa", "200", coleccionPublicaResponse));
  }

  //TODO
  /*
  - Consultar por tipo PUBLICA(Codigo, creador), COMPARTIDA(Codigo, creador), PRIVADA -> todas muestran nombre de la coleccion
  - Crear una coleccion privada
  - Actualizar coleccion privada a publica o compartida
   */

}
