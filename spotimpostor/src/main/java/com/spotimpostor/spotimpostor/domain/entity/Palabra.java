package com.spotimpostor.spotimpostor.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "palabras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Palabra {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30, nullable = false)
  private String palabra;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_coleccion", nullable = false)
  private Coleccion coleccion;

  @OneToMany(mappedBy = "palabra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<PalabraPista> pistas;
}
