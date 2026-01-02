package com.spotimpostor.spotimpostor.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModoPartidaResponse {
  private String modo;
  private String descripcion;
}
