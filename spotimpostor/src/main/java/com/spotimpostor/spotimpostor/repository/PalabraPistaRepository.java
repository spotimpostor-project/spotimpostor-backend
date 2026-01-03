package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.PalabraPista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalabraPistaRepository extends JpaRepository<PalabraPista, Long> {

}
