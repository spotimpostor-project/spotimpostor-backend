package com.spotimpostor.spotimpostor.config;

import com.spotimpostor.spotimpostor.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    // 1. Extraer el encabezado Authorization
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    // 2. Validar que el encabezado exista y empiece con "Bearer "
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // 3. Obtener el token (saltando los primeros 7 caracteres de "Bearer ")
    jwt = authHeader.substring(7);
    System.out.println(jwt); //TODO: PARA REVISAR
    userEmail = jwtService.extractUsername(jwt);

    // 4. Si hay email y el usuario aún no está autenticado en el contexto
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      // 5. Validar si el token es correcto y no ha expirado
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 6. Establecer al usuario como autenticado en Spring Security
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // Continuar con el siguiente filtro en la cadena
    filterChain.doFilter(request, response);
  }
}
