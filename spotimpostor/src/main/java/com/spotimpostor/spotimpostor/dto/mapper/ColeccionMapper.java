package com.spotimpostor.spotimpostor.dto.mapper;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionUsuarioResponse;
import com.spotimpostor.spotimpostor.dto.response.BuscarMisColeccionesResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoColeccionPublicaResponse;
import org.springframework.stereotype.Component;

@Component
public class ColeccionMapper {

  public BuscarColeccionUsuarioResponse mapColeccionPublica(ColeccionUsuario coleccionUsuario) {
    return BuscarColeccionUsuarioResponse.builder()
            .nombre(coleccionUsuario.getColeccion().getNombre())
            .codigo(coleccionUsuario.getCodigo())
            .autor(coleccionUsuario.getUsuario().getNombre())
            .visibilidad(coleccionUsuario.getColeccion().getTipo())
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

  public BuscarMisColeccionesResponse mapMiColeccion(ColeccionUsuario coleccionUsuario) {
    return BuscarMisColeccionesResponse.builder()
            .nombre(coleccionUsuario.getColeccion().getNombre())
            .codigo(coleccionUsuario.getCodigo())
            .visibilidad(coleccionUsuario.getColeccion().getTipo())
            .build();
  }

  public Coleccion buildColeccion(String nombreColeccion) {
    Coleccion coleccion = new Coleccion();
    coleccion.setNombre(nombreColeccion);
    coleccion.setTipo(TipoColeccion.PRIVADA);
    return coleccion;
  }

  public ColeccionUsuario buildColeccionUsuario(Coleccion coleccion, Usuario usuario, String codigo) {
    return ColeccionUsuario.builder()
            .coleccion(coleccion)
            .usuario(usuario)
            .codigo(codigo)
            .build();
  }
}
