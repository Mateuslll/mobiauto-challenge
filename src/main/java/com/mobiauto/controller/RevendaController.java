package com.mobiauto.controller;

import com.mobiauto.dto.RevendaDTO;
import com.mobiauto.model.Revenda;
import com.mobiauto.service.RevendaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/revendas")
public class RevendaController {

    @Autowired
    private RevendaService revendaService;

    @PostMapping
    public ResponseEntity<Revenda> createRevenda(@RequestBody @Valid RevendaDTO revendaDto) {
        Revenda revenda = new Revenda();
        BeanUtils.copyProperties(revendaDto, revenda);
        Revenda savedRevenda = revendaService.saveRevenda(revenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRevenda);
    }

    @GetMapping
    public ResponseEntity<List<Revenda>> getAllRevendas() {
        List<Revenda> revendas = revendaService.getAllRevendas();
        return ResponseEntity.ok(revendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenda> getRevendaById(@PathVariable Long id) {
        Optional<Revenda> revenda = revendaService.getRevendaById(id);
        return revenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevenda(@PathVariable Long id) {
        revendaService.deleteRevenda(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revenda> updateRevenda(@PathVariable Long id, @RequestBody @Valid RevendaDTO revendaDto) {
        Optional<Revenda> existingRevenda = revendaService.getRevendaById(id);
        if (existingRevenda.isPresent()) {
            Revenda revenda = existingRevenda.get();
            BeanUtils.copyProperties(revendaDto, revenda);
            revenda.setId(id);
            Revenda updatedRevenda = revendaService.saveRevenda(revenda);
            return ResponseEntity.ok(updatedRevenda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
