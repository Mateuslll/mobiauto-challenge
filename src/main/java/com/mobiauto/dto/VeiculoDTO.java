package com.mobiauto.dto;

import jakarta.validation.constraints.NotBlank;

public record VeiculoDTO(
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotBlank String versao,
        @NotBlank String anoModelo
){}