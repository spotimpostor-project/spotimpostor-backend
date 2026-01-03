package com.spotimpostor.spotimpostor.domain.entity;

import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "colecciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 30, nullable = false, unique = true)
  private String nombre;

  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private TipoColeccion tipo;

  @OneToOne(mappedBy = "coleccion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  private ColeccionUsuario coleccionUsuario;

  @OneToMany(mappedBy = "coleccion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Palabra> palabras;
}
