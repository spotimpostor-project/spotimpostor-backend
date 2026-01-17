package com.spotimpostor.spotimpostor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PalabraDTO {
  private Long id;
  @NotBlank(message = "La palabra no puede estar vacía")
  private String palabra;
}
