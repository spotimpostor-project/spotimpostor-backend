package com.spotimpostor.spotimpostor.repository;


import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import com.spotimpostor.spotimpostor.domain.entity.Palabra;
import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColeccionUsuarioRepository extends JpaRepository<ColeccionUsuario, Long> {
  Optional<ColeccionUsuario> findByCodigo(String codigo);
  Boolean existsByCodigo(String codigo);

  @Query("SELECT cu FROM ColeccionUsuario cu " +
          "JOIN FETCH cu.coleccion " +
          "WHERE cu.usuario = :usuario")
  List<ColeccionUsuario> findWithColeccionByUsuario(@Param("usuario") Usuario usuario);

  @Query("SELECT p FROM ColeccionUsuario cu JOIN cu.coleccion c "+
          "JOIN c.palabras p WHERE cu.codigo = :codigo")
  List<Palabra> getPalabrasByCodigo(@Param("codigo") String codigo);

  Optional<ColeccionUsuario> findByUsuarioCorreoAndCodigo(String correo, String codigo);

  @Query(value = """
              SELECT cu.* 
              FROM vista_colecciones_publicas v
              INNER JOIN colecciones_usuarios cu ON v.codigo = cu.codigo
              ORDER BY 
                  v.nombre <-> :query,
                  v.total_partidas DESC
              LIMIT 20
          """, nativeQuery = true)
  List<ColeccionUsuario> buscarPorNombrePopularidad(@Param("query") String query);

  @Query(value = """
              SELECT cu.*
              FROM colecciones_usuarios cu
              INNER JOIN colecciones c
              	ON c.id = cu.id_coleccion
              WHERE c.tipo = 'PUBLICA'
              ORDER BY 
                  c.nombre <-> :query, 
                  cu.fecha_creacion desc
              LIMIT 20
          """, nativeQuery = true)
  List<ColeccionUsuario> buscarPorNombreReciente(@Param("query") String query);
}
