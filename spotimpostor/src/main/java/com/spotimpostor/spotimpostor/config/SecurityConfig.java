package com.spotimpostor.spotimpostor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable) // Desactiva la protección contra ataques cruzados (obligatorio para Postman)
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // LIBERA todas las rutas del proyecto
            );
    return http.build();
  }
}