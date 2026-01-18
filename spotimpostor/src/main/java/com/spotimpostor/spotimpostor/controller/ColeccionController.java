package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.dto.request.CambiarVisibilidadRequest;
import com.spotimpostor.spotimpostor.dto.request.PalabraDTO;
import com.spotimpostor.spotimpostor.dto.request.RegistrarColeccionRequest;
import com.spotimpostor.spotimpostor.dto.request.UpdateColeccionRequest;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.dto.response.BuscarMisColeccionesResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.service.ColeccionService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

  @GetMapping("/usuario")
  public ResponseEntity<ApiResponse<List<BuscarMisColeccionesResponse>>> consultarMisColecciones (
    Authentication authentication
  ) {
    List<BuscarMisColeccionesResponse> misColecciones = coleccionService.findMisColecciones(authentication.getName());
    return ResponseEntity.ok(new ApiResponse<>("Consulta exitosa de mis colecciones", "200", misColecciones));
  }

  @GetMapping("/usuario/{codigo}")
  public ResponseEntity<ApiResponse<List<PalabraDTO>>> consultarDetallesMiColeccion (
          @PathVariable String codigo
  ) {
    List<PalabraDTO> palabraDTO = coleccionService.getPalabrasMiColeccion(codigo);
    return ResponseEntity.ok(new ApiResponse<>("Consulta exitosa de mis colecciones", "200", palabraDTO));
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
  public ResponseEntity<ApiResponse<BuscarMisColeccionesResponse>> registrar(
    @Valid @RequestBody RegistrarColeccionRequest dtoRequest,
    Authentication authentication
  ) {
    //String correo = (authentication != null) ? authentication.getName() : null;
    String correo = authentication.getName();

    BuscarMisColeccionesResponse response = coleccionService.registerColeccion(dtoRequest, correo);
    return ResponseEntity.ok(new ApiResponse<>("Registro exitoso", "200", response));
  }

  @PatchMapping("/usuario/{codigo}")
  public ResponseEntity<ApiResponse<BuscarMisColeccionesResponse>> actualizar(
          @Valid @RequestBody UpdateColeccionRequest dtoRequest,
          @PathVariable String codigo,
          Authentication authentication
  ) {
    BuscarMisColeccionesResponse response = coleccionService.updateColeccion(dtoRequest, authentication.getName(), codigo);
    return ResponseEntity.ok(new ApiResponse<>("Actualizacion exitosa", "200", response));
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
