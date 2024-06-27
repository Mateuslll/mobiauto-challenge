package com.mobiauto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank String telefone
) {}
