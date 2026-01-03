package com.spotimpostor.spotimpostor.repository;


import com.spotimpostor.spotimpostor.domain.entity.ColeccionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColeccionUsuarioRepository extends JpaRepository<ColeccionUsuario, Long> {
  Optional<ColeccionUsuario> findByCodigo(String codigo);
  Boolean existsByCodigo(String codigo);
}
