package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import com.spotimpostor.spotimpostor.dto.request.RegistrarColeccionRequest;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoColeccionPublicaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColeccionMapper {

  public BuscarColeccionPublicaResponse mapColeccionPublica(Coleccion coleccion) {
    return BuscarColeccionPublicaResponse.builder()
            .nombre(coleccion.getNombre())
            .codigo(coleccion.getColeccionUsuario().getCodigo())
            .build();
  }

  public InfoColeccionPublicaResponse mapSpecificColeccionPublica(ColeccionUsuario coleccionUsuario) {
    return InfoColeccionPublicaResponse.builder()
            .nombre(coleccionUsuario.getColeccion().getNombre())
            .codigo(coleccionUsuario.getCodigo())
            .creador(coleccionUsuario.getUsuario().getNombre())
            .palabras(coleccionUsuario.getColeccion().getPalabras().stream().map(Palabra::getPalabra).toList())
            .build();
  }

  public Coleccion buildColeccion(String nombreColeccion) {
    Coleccion coleccion = new Coleccion();
    coleccion.setNombre(nombreColeccion);
    coleccion.setTipo(TipoColeccion.PRIVADA);
    return coleccion;
  }

  public ColeccionUsuario buildColeccionUsuario(Coleccion coleccion, Usuario usuario, String codigo) {
    ColeccionUsuario coleccionUsuario = new ColeccionUsuario();
    coleccionUsuario.setColeccion(coleccion);
    coleccionUsuario.setUsuario(usuario);
    coleccionUsuario.setCodigo(codigo);
    return coleccionUsuario;
  }
}
