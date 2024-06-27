package com.mobiauto.repository;

import com.mobiauto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.cargo = 'ASSISTENTE'")
    List<Usuario> findAssistentesDisponiveis();
}
