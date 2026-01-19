package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModoPartidaRepository extends JpaRepository<ModoPartida, Integer> {
  Optional<ModoPartida> findByModo(String modo);
}
