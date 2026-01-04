package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, UUID> {
}
