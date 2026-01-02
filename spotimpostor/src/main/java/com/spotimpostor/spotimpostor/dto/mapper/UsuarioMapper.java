package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.dto.request.CreateUsuarioRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoUsuarioResponse;

public class UsuarioMapper {

  public static Usuario buildUsuario(CreateUsuarioRequest dtoRequest, String hashedPsw) {
    Usuario usuario = new Usuario();
    //usuario.setId(IdGenerator.generateId());
    usuario.setNombre(dtoRequest.getNombre());
    usuario.setCorreo(dtoRequest.getCorreo());
    usuario.setPassword(hashedPsw);
    return usuario;
  }

  public static InfoUsuarioResponse mapUsuario(Usuario usuario){
    return InfoUsuarioResponse.builder()
            .nombre(usuario.getNombre())
            .correo(usuario.getCorreo())
            .build();
  }
}
