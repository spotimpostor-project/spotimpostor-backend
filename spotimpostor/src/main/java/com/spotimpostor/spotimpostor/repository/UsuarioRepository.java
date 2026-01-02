package com.spotimpostor.spotimpostor.repository;

import com.spotimpostor.spotimpostor.domain.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  Optional<Usuario> findByNombre(String nombre);
  Optional<Usuario> findByCorreo(String correo);

  @Query("SELECT u.password FROM Usuario u WHERE u.correo = :correo")
  String findPswByCorreo(@Param("correo") String correo);

  Boolean existsByNombre (String nombre);
  Boolean existsByCorreo (String correo);

  @Modifying
  @Query("UPDATE Usuario u SET u.nombre = :nombre WHERE u.correo = :correo")
  void updateUsername (@Param("nombre") String nombre, @Param("correo") String correo);

  @Modifying
  @Query("UPDATE Usuario u SET u.password = :password WHERE u.correo = :correo")
  void updatePassword (@Param("password") String password, @Param("correo") String correo);
}
