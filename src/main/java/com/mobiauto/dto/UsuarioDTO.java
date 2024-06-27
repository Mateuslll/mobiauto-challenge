package com.mobiauto.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String cargo
) {}
