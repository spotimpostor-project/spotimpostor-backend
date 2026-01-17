package com.spotimpostor.spotimpostor.service;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import com.spotimpostor.spotimpostor.dto.mapper.ColeccionMapper;
import com.spotimpostor.spotimpostor.dto.request.CambiarVisibilidadRequest;
import com.spotimpostor.spotimpostor.dto.request.PalabraDTO;
import com.spotimpostor.spotimpostor.dto.request.RegistrarColeccionRequest;
import com.spotimpostor.spotimpostor.dto.request.UpdateColeccionRequest;
import com.spotimpostor.spotimpostor.dto.response.BuscarColeccionPublicaResponse;
import com.spotimpostor.spotimpostor.dto.response.BuscarMisColeccionesResponse;
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
import java.util.Map;
import java.util.Optional;
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
    return coleccionRepository.findNombresByTipo(GENERAL);
    /*List<Coleccion> coleccions = coleccionRepository.findByTipo(GENERAL);
    return coleccions.stream().map(Coleccion::getNombre).collect(Collectors.toList());
     */
  }

  //TODO: CHECK QUANTITY OF LOGS
  public List<BuscarColeccionPublicaResponse> findColeccionesPublicas() {
    List<Coleccion> coleccions = coleccionRepository.findByTipo(PUBLICA);
    return coleccions.stream().map(mapper::mapColeccionPublica).collect(Collectors.toList());
  }

  @Transactional
  public List<BuscarMisColeccionesResponse> findMisColecciones(String correoUsuario) {

    Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
            .orElseThrow(() -> new NotFoundException("No se encontró usuario con correo "+correoUsuario));

    List<ColeccionUsuario> coleccionesUsuario = coleccionUsuarioRepository
            .findWithColeccionByUsuario(usuario);

    return coleccionesUsuario.stream()
            .map(mapper::mapMiColeccion)
            .toList();
  }

  public List<PalabraDTO> getPalabrasMiColeccion(String codigo) {
    return coleccionUsuarioRepository.getPalabrasByCodigo(codigo).stream()
            .map(PalabraMapper::mapPalabraDTO).toList();
  }

  public InfoColeccionPublicaResponse getDetalleColeccionUsuario(String codigo) {
    return mapper.mapSpecificColeccionPublica(coleccionUsuarioRepository.findByCodigo(codigo)
            .orElseThrow(() -> new NotFoundException("No se encontró colección con código "+codigo)));
  }

  @Transactional
  public InfoColeccionPublicaResponse registerColeccion(RegistrarColeccionRequest dtoRequest, String correoUsuario) {

    Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
            .orElseThrow(() -> new NotFoundException("No se encontró usuario con correo "+correoUsuario));

    Coleccion coleccion = mapper.buildColeccion(dtoRequest.getNombreColeccion());
    ColeccionUsuario coleccionUsuario = mapper.buildColeccionUsuario(coleccion, usuario, generarCodigo());

    coleccion.setColeccionUsuario(coleccionUsuario);

    List<Palabra> palabras = dtoRequest.getPalabras().stream()
            .map(s -> PalabraMapper.buildPalabra(s, coleccion)).collect(Collectors.toList());

    coleccion.setPalabras(palabras);

    coleccionRepository.save(coleccion);

    return mapper.mapSpecificColeccionPublica(coleccionUsuario);
  }

  @Transactional
  public InfoColeccionPublicaResponse updateVisibilidad(CambiarVisibilidadRequest dtoRequest) {
    Coleccion coleccion = coleccionRepository.findByCodigo(dtoRequest.getCodigo())
            .orElseThrow(() -> new NotFoundException("No se encontró colección con código "+dtoRequest.getCodigo()));

    coleccion.setTipo(dtoRequest.getTipoColeccion());
    coleccionRepository.save(coleccion);

    return getDetalleColeccionUsuario(dtoRequest.getCodigo());
  }

  @Transactional
  public BuscarMisColeccionesResponse updateColeccion(UpdateColeccionRequest dtoRequest, String correo, String codigo) {
    ColeccionUsuario coleccionUsuario = coleccionUsuarioRepository.findByUsuarioCorreoAndCodigo(correo, codigo)
            .orElseThrow(() -> new NotFoundException("No tienes permiso para editar la colección"));

    Coleccion coleccion = coleccionUsuario.getColeccion();

    coleccion.setNombre(dtoRequest.getNombre());
    coleccion.setTipo(dtoRequest.getVisibilidad());

    sincronizarPalabras(coleccion, dtoRequest.getPalabras());

    coleccionRepository.save(coleccion);
    return BuscarMisColeccionesResponse.builder()
            .nombre(dtoRequest.getNombre())
            .visibilidad(dtoRequest.getVisibilidad())
            .codigo(codigo)
            .build();
  }

  /*
  private Boolean esCreadorColeccion(String correo, String codigo) {
    return coleccionUsuarioRepository.existsByUsuarioCorreoAndCodigo(correo, codigo);
  }
  */

  private void sincronizarPalabras(Coleccion coleccion, List<PalabraDTO> palabrasDTO) {
    // 1. Palabras actuales -> Map de acceso rápido
    Map<Long, Palabra> palabrasActuales = coleccion.getPalabras().stream()
            .collect(Collectors.toMap(Palabra::getId, p -> p));

    // 2. Lista final de palabras
    List<Palabra> listaFinal = new ArrayList<>();

    for (PalabraDTO dto : palabrasDTO) {
      if (dto.getId() != null && palabrasActuales.containsKey(dto.getId())) {
        // ACTUALIZAR EXISTENTE
        Palabra p = palabrasActuales.get(dto.getId());
        p.setPalabra(dto.getPalabra());
        listaFinal.add(p);
      } else {
        // AGREGAR NUEVA
        Palabra nueva = Palabra.builder()
                .palabra(dto.getPalabra())
                .coleccion(coleccion)
                .build();
        listaFinal.add(nueva);
      }
    }

    // 3. Limpiar y reemplazar
    coleccion.getPalabras().clear();
    coleccion.getPalabras().addAll(listaFinal);
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
  - Cambiar visibilidad de la coleccion

  - Registrar palabras
   */

}
