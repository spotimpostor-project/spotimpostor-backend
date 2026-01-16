package com.spotimpostor.spotimpostor.controller;

import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.dto.request.CambiarPasswordRequest;
import com.spotimpostor.spotimpostor.dto.request.CambiarUsernameRequest;
import com.spotimpostor.spotimpostor.dto.request.CreateUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.request.ConsultarUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoUsuarioResponse;
import com.spotimpostor.spotimpostor.dto.response.LoginResponse;
import com.spotimpostor.spotimpostor.service.JwtService;
import com.spotimpostor.spotimpostor.service.UsuarioService;
import com.spotimpostor.spotimpostor.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

  private final AuthenticationManager authenticationManager;
  private final UsuarioService usuarioService;
  private final JwtService jwtService;

  @PostMapping("/auth/register")
  public ResponseEntity<ApiResponse<String>> registrar(
    @Valid @RequestBody CreateUsuarioRequest createUsuarioRequest
  ) {
    usuarioService.registerUser(createUsuarioRequest);

    var user = usuarioService.obtenerEntidadPorCorreo(createUsuarioRequest.getCorreo());
    String jwtToken = jwtService.generateToken(user);

    //InfoUsuarioResponse infoUsuarioResponse = usuarioService.registerUser(createUsuarioRequest);
    return ResponseEntity.ok(new ApiResponse<>("Se registró el usuario exitosamente", "200", jwtToken));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
    @Valid @RequestBody ConsultarUsuarioRequest consultarUsuarioRequest
  ) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
                consultarUsuarioRequest.getCorreo(),
                consultarUsuarioRequest.getPassword()
        )
    );

    Usuario user = usuarioService.obtenerEntidadPorCorreo(consultarUsuarioRequest.getCorreo());
    String jwtToken = jwtService.generateToken(user);

    //Boolean valido = usuarioService.validarUser(consultarUsuarioRequest);
    return ResponseEntity.ok(new ApiResponse<>("Autenticación exitosa", "200",
            LoginResponse.builder().token(jwtToken).nombre(user.getNombre()).build()));
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
