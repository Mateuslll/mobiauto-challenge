package com.mobiauto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RevendaDTO(
        @NotBlank(message = "O CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve ter 14 dígitos")
        String cnpj,

        @NotBlank(message = "O nome social é obrigatório")
        String nomeSocial) {
}
