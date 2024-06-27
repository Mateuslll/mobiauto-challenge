package com.mobiauto.service;

import com.mobiauto.model.Revenda;
import com.mobiauto.repository.RevendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevendaService {

    @Autowired
    private RevendaRepository revendaRepository;

    public Revenda saveRevenda(Revenda revenda) {
        return revendaRepository.save(revenda);
    }

    public List<Revenda> getAllRevendas() {
        return revendaRepository.findAll();
    }

    public Optional<Revenda> getRevendaById(Long id) {
        return revendaRepository.findById(id);
    }

    public Optional<Revenda> getRevendaByCnpj(String cnpj) {
        return revendaRepository.findByCnpj(cnpj);
    }

    public void deleteRevenda(Long id) {
        revendaRepository.deleteById(id);
    }
}