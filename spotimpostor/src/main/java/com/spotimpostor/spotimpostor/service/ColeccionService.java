package com.spotimpostor.spotimpostor.service;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import com.spotimpostor.spotimpostor.dto.mapper.ColeccionMapper;
import com.spotimpostor.spotimpostor.dto.request.CambiarVisibilidadRequest;
import com.spotimpostor.spotimpostor.dto.request.RegistrarColeccionRequest;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.dto.response.InfoColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.exception.NotFoundException;
import com.spotimpostor.spotimpostor.repository.ColeccionRepository;
import com.spotimpostor.spotimpostor.repository.ColeccionUsuarioRepository;
import com.spotimpostor.spotimpostor.repository.PalabraRepository;
import com.spotimpostor.spotimpostor.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spotimpostor.spotimpostor.dto.mapper.PalabraMapper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spotimpostor.spotimpostor.domain.enums.TipoColeccion.GENERAL;
import static com.spotimpostor.spotimpostor.domain.enums.TipoColeccion.PUBLICA;

@Service
@RequiredArgsConstructor
public class ColeccionService {

  private final ColeccionRepository coleccionRepository;
  private final ColeccionUsuarioRepository coleccionUsuarioRepository;
  private final UsuarioRepository usuarioRepository;
  private final PalabraRepository palabraRepository;
  private final ColeccionMapper mapper;

  public List<String> findColeccionesGenerales() {
    List<Coleccion> coleccions = coleccionRepository.findByTipo(GENERAL);
    return coleccions.stream().map(Coleccion::getNombre).collect(Collectors.toList());
  }

  public List<BuscarColeccionPublicaResponse> findColeccionesPublicas() {
    List<Coleccion> coleccions = coleccionRepository.findByTipo(PUBLICA);
    return coleccions.stream().map(mapper::mapColeccionPublica).collect(Collectors.toList());
  }

  public InfoColeccionPublicaResponse getDetalleColeccionUsuario(String codigo) {
    return mapper.mapSpecificColeccionPublica(coleccionUsuarioRepository.findByCodigo(codigo)
            .orElseThrow(() -> new NotFoundException("No se encontró colección con código "+codigo)));
  }

  @Transactional
  public InfoColeccionPublicaResponse registerColeccion(RegistrarColeccionRequest dtoRequest) {

    Usuario usuario = usuarioRepository.findByCorreo(dtoRequest.getCorreoUsuario())
            .orElseThrow(() -> new NotFoundException("No se encontró usuario con correo "+dtoRequest.getCorreoUsuario()));

    Coleccion coleccion = mapper.buildColeccion(dtoRequest.getNombreColeccion());

    //String codigo = generarCodigo();

    ColeccionUsuario coleccionUsuario = mapper.buildColeccionUsuario(coleccion, usuario, generarCodigo());

    coleccion.setColeccionUsuario(coleccionUsuario);

    List<Palabra> palabras = dtoRequest.getPalabras().stream()
            .map(s -> PalabraMapper.buildPalabra(s, coleccion)).collect(Collectors.toList());

    coleccion.setPalabras(palabras);

    coleccionRepository.save(coleccion);

    return mapper.mapSpecificColeccionPublica(coleccionUsuario);
  }

  public InfoColeccionPublicaResponse updateVisibilidad(CambiarVisibilidadRequest dtoRequest) {
    Coleccion coleccion = coleccionRepository.findByCodigo(dtoRequest.getCodigo())
            .orElseThrow(() -> new NotFoundException("No se encontró colección con código "+dtoRequest.getCodigo()));

    coleccion.setTipo(dtoRequest.getTipoColeccion());
    coleccionRepository.save(coleccion);

    return getDetalleColeccionUsuario(dtoRequest.getCodigo());
  }




  private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();

  private String generarCodigo() {
    String nuevoCodigo;
    boolean existe = false;

    do {
      nuevoCodigo = RANDOM.ints(7,0, ALFABETO.length())
              .mapToObj(ALFABETO::charAt)
              .map(Object::toString)
              .collect(Collectors.joining());

      existe = coleccionUsuarioRepository.existsByCodigo(nuevoCodigo);
    } while (existe);

    return nuevoCodigo;
  }




  /*
  public List<String> consultarPorTipo(String tipo) {
    List<Coleccion> coleccions = coleccionRepository.findByTipo(tipo);
    return coleccions.stream().map(Coleccion::getNombre).collect(Collectors.toList());
  }
   */


  //TODO
  /*
  - Registrar Coleccion: Requiere nombre, codigo (autogenerado), usuario, Lista de palabras (minimo 2)
  ** No hay pistas aqui

  - Realmente la pista forma un primary key compuesto?

  - Cambiar visibilidad de la coleccion

  - Registrar palabras
   */

}
