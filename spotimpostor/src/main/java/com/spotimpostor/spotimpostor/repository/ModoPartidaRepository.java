package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.ModoPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModoPartidaRepository extends JpaRepository<ModoPartida, Integer> {
}
