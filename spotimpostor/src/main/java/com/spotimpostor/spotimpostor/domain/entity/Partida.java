package com.spotimpostor.spotimpostor.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partida {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_modo", nullable = false)
  private ModoPartida modoPartida;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_coleccion", nullable = false)
  private Coleccion coleccion;

  @Column(name = "cantidad_jugadores", nullable = false)
  private Integer cantidadJugadores;

  @Column(name = "cantidad_impostores", nullable = false)
  private Integer cantidadImpostores;

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDate fechaCreacion;

  @Column
  private LocalTime duracion;

  @Column(name = "es_victoria")
  private Boolean esVictoria;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;
}
