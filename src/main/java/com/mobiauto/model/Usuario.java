package com.mobiauto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String cargo;

    @ManyToMany
    @JoinTable(
            name = "usuario_revenda",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "revenda_id")
    )
    private Set<Revenda> revendas;


    @OneToMany(mappedBy = "responsavel")
    private List<Oportunidade> oportunidadesEmAndamento;

    @Setter
    @Getter
    private LocalDateTime ultimaOportunidadeRecebida;

}
