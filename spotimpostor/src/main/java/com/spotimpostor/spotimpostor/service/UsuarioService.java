package com.spotimpostor.spotimpostor.service;

import com.spotimpostor.spotimpostor.dto.mapper.UsuarioMapper;
import com.spotimpostor.spotimpostor.dto.request.CambiarPasswordRequest;
import com.spotimpostor.spotimpostor.dto.request.CambiarUsernameRequest;
import com.spotimpostor.spotimpostor.dto.request.CreateUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.request.ConsultarUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoUsuarioResponse;
import com.spotimpostor.spotimpostor.exception.ConflictException;
import com.spotimpostor.spotimpostor.exception.NotFoundException;
import com.spotimpostor.spotimpostor.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  @Transactional
  public InfoUsuarioResponse getUser(String correo) {
    return UsuarioMapper.mapUsuario(usuarioRepository.findByCorreo(correo).
            orElseThrow(() -> new NotFoundException("No se encontró usuario con correo: "+correo))
    );
  }

  @Transactional
  public InfoUsuarioResponse registerUser(CreateUsuarioRequest dtoRequest) {
    if (usuarioRepository.existsByNombre(dtoRequest.getNombre()) || usuarioRepository.existsByCorreo(dtoRequest.getCorreo())) {
      throw new ConflictException("Nombre y(o) correo inválido");
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPsw = encoder.encode(dtoRequest.getPassword());
    return UsuarioMapper.mapUsuario(usuarioRepository.save(UsuarioMapper.buildUsuario(dtoRequest, hashedPsw)));
  }

  @Transactional
  public Boolean validarUser(ConsultarUsuarioRequest dtoRequest) {
    String hashPsw = usuarioRepository.findPswByCorreo(dtoRequest.getCorreo());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(dtoRequest.getPassword(),hashPsw);
  }

  @Transactional
  public InfoUsuarioResponse updateNombre(CambiarUsernameRequest dtoRequest) {
    usuarioRepository.updateUsername(dtoRequest.getNombre(), dtoRequest.getCorreo());
    return getUser(dtoRequest.getCorreo());
  }

  @Transactional
  public InfoUsuarioResponse updatePassword(CambiarPasswordRequest dtoRequest) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPsw = encoder.encode(dtoRequest.getPassword());
    usuarioRepository.updatePassword(hashedPsw, dtoRequest.getCorreo());
    return getUser(dtoRequest.getCorreo());
  }
}
