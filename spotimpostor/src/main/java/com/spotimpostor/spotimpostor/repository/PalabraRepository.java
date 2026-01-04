package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Long> {

  @Query(nativeQuery = true, value =
          "SELECT * FROM palabras WHERE id_coleccion = :coleccionId ORDER BY RANDOM() LIMIT 1")
  Optional<Palabra> findRandomPalabraByColeccionId(@Param("coleccionId") Long coleccionId);
}
