package com.mobiauto.dto;

import com.mobiauto.model.StatusOportunidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record OportunidadeDTO(
        @NotBlank String codigo,
        @NotNull StatusOportunidade status,
        String motivoConclusao,
        @NotBlank String clienteNome,
        @Email @NotBlank String clienteEmail,
        @NotBlank String clienteTelefone,
        @NotBlank String veiculoMarca,
        @NotBlank String veiculoModelo,
        @NotBlank String veiculoVersao,
        @NotNull int veiculoAnoModelo,
        Long responsavelId // Adicionando o ID do responsável
) {}
