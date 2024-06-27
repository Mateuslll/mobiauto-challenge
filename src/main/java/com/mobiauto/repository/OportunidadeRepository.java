package com.mobiauto.repository;

import com.mobiauto.model.Oportunidade;
import com.mobiauto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    long countByResponsavelAndStatus(Usuario responsavel, String status);
    Optional<Oportunidade> findTopByResponsavelOrderByDataAtribuicaoDesc(Usuario responsavel);
}
