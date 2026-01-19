package com.spotimpostor.spotimpostor.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "modos_partida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModoPartida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 10, nullable = false)
  private String modo;

  @Column(length = 250, nullable = false)
  private String descripcion;
}
