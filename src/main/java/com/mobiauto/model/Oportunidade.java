package com.mobiauto.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "revenda_id")
    private Revenda revenda;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Enumerated(EnumType.STRING)
    private StatusOportunidade status;

    private String motivoConclusao;

    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;
    private String codigo;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private String versaoVeiculo;
    private int anoModeloVeiculo;

    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataConclusao;

}
