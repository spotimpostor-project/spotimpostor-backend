package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Coleccion;
import com.spotimpostor.spotimpostor.domain.enums.TipoColeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion, Long> {

  @Query("SELECT c.nombre FROM Coleccion c WHERE c.tipo = :tipo")
  List<String> findNombresByTipo(@Param("tipo") TipoColeccion tipo);

  List<Coleccion> findByTipo(TipoColeccion tipoColeccion);

  @Query("SELECT c FROM Coleccion c JOIN c.coleccionUsuario cu WHERE cu.codigo = :codigo")
  Optional<Coleccion> findByCodigo(@Param("codigo") String codigo);

  @Query("SELECT c FROM Coleccion c WHERE c.nombre = :nombre")
  Optional<Coleccion> findByNombre(@Param("nombre") String nombre);

}
