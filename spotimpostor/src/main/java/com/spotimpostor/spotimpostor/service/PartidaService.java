package com.spotimpostor.spotimpostor.service;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.PalabraPista;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import com.spotimpostor.spotimpostor.dto.mapper.PartidaMapper;
import com.spotimpostor.spotimpostor.dto.request.CreatePartidaRequest;
import com.spotimpostor.spotimpostor.dto.response.InfoPartidaResponse;
import com.spotimpostor.spotimpostor.dto.response.PalabrasResponse;
import com.spotimpostor.spotimpostor.exception.NotFoundException;
import com.spotimpostor.spotimpostor.repository.ColeccionRepository;
import com.spotimpostor.spotimpostor.repository.ModoPartidaRepository;
import com.spotimpostor.spotimpostor.repository.PalabraPistaRepository;
import com.spotimpostor.spotimpostor.repository.PalabraRepository;
import com.spotimpostor.spotimpostor.repository.PartidaRepository;
import com.spotimpostor.spotimpostor.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PartidaService {

  private final PartidaRepository partidaRepository;
  private final ColeccionRepository coleccionRepository;
  private final UsuarioRepository usuarioRepository;
  private final ModoPartidaRepository modoPartidaRepository;
  private final PalabraRepository palabraRepository;
  private final PalabraPistaRepository palabraPistaRepository;

  @Transactional
  public List<InfoPartidaResponse> registrarPartida(CreatePartidaRequest dtoRequest, String correo) {
    Coleccion coleccion = (dtoRequest.getTipoColeccion() == TipoColeccion.GENERAL) ?
            coleccionRepository.findByNombre(dtoRequest.getNombreColeccion())
                    .orElseThrow(() -> new NotFoundException("No se encontró la colección"))
            :
            coleccionRepository.findByCodigo(dtoRequest.getCodigoColeccion())
                    .orElseThrow(() -> new NotFoundException("No se encontró la colección"));

    Usuario usuario = null;

    if (!Objects.isNull(correo)) {
      usuario = usuarioRepository.findByCorreo(correo).
              orElseThrow(() -> new NotFoundException("No se encontró usuario con correo: "+correo));
    }
    /*
    Usuario usuario = usuarioRepository.findByCorreo(dtoRequest.getCorreo()).
            orElseThrow(() -> new NotFoundException("No se encontró usuario con correo: "+dtoRequest.getCorreo()));
     */

    ModoPartida modo = modoPartidaRepository.findByModo(dtoRequest.getModo())
            .orElseThrow(() -> new NotFoundException("No se encontró el modo de juego: "+dtoRequest.getModo()));

    partidaRepository.save(PartidaMapper.buildPartida(dtoRequest, usuario, coleccion, modo));

    PalabrasResponse palabrasResponse = getPalabrasAleatorias(dtoRequest.getTipoColeccion(), coleccion);

    // Crear una lista de índices (0, 1, 2, ..., n) y mezclarlos
    List<Integer> indicesAleatorios = IntStream.range(0, dtoRequest.getCantidadJugadores())
            .boxed()
            .collect(Collectors.toList());
    Collections.shuffle(indicesAleatorios);

    // Tomar los primeros 'n' índices como los elegidos para ser impostores
    List<Integer> indicesImpostores = indicesAleatorios.subList(0, dtoRequest.getCantidadImpostores());

    // Mapear la lista original al DTO de respuesta usando el índice original
    return IntStream.range(0, dtoRequest.getCantidadJugadores())
            .mapToObj(i -> {
              boolean esImpostor = indicesImpostores.contains(i);

              return InfoPartidaResponse.builder()
                      .jugador(dtoRequest.getJugadores().get(i)) // Mantiene el orden original
                      .rol(esImpostor ? "Impostor" : "Civil")
                      .palabra(esImpostor ? palabrasResponse.getPista().toUpperCase() : palabrasResponse.getPalabra().toUpperCase())
                      .build();
            })
            .collect(Collectors.toList());
  }

  public PalabrasResponse getPalabrasAleatorias(TipoColeccion tipoColeccion, Coleccion coleccion) {

    Palabra palabra = palabraRepository.findRandomPalabraByColeccionId(coleccion.getId())
            .orElseThrow(() -> new NotFoundException("No se encontró palabra para la coleccion "+coleccion.getNombre()));

    String textoPista = "No hay pista para este tipo de coleccion";

    if (tipoColeccion == TipoColeccion.GENERAL) {
      textoPista = palabraPistaRepository.findRandomPista(palabra.getPalabra(), coleccion.getId())
              .orElseThrow(() -> new NotFoundException("Pista faltante para: " + palabra.getPalabra()));
    }

    return PalabrasResponse.builder()
            .palabra(palabra.getPalabra())
            .pista(textoPista)
            .build();
  }

  //TODO
  //1. El tamaño de la lista de jugadores debe coincidir con lo que se ha definido, o cambiará el diseño en ese sentido?
  //   Tal vez se maneje por el length de la Lista de jugadores

  //2. El frontend recibe toda la informacion de los jugadores y sus roles, se debe tener cuidado para cubrirlo

}
