package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.PalabraPista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PalabraPistaRepository extends JpaRepository<PalabraPista, Long> {
  @Query(nativeQuery = true, value =
          "SELECT pp.palabra_pista FROM palabras_pistas pp JOIN palabras p ON pp.id_palabra = p.id \n" +
                  "WHERE p.id_coleccion = :coleccionId AND p.palabra = :palabra ORDER BY RANDOM() LIMIT 1")
  Optional<String> findRandomPista(@Param("palabra") String palabra, @Param("coleccionId") Long coleccionId);
}
