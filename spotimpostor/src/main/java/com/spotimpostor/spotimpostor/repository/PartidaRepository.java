package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, UUID> {

  @Modifying
  @Transactional
  @Query("UPDATE Partida p SET p.duracion = :duracion, p.esVictoria = :esVictoria WHERE p.id = :id")
  void updatePartidaResultados(
          @Param("id") UUID id,
          @Param("duracion") LocalTime duracion,
          @Param("esVictoria") Boolean esVictoria
  );

}
