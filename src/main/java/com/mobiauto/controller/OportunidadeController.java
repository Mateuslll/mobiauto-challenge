package com.mobiauto.controller;

import com.mobiauto.dto.OportunidadeDTO;
import com.mobiauto.model.Oportunidade;
import com.mobiauto.service.OportunidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/oportunidades")
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    @Autowired
    public OportunidadeController(OportunidadeService oportunidadeService) {
        this.oportunidadeService = oportunidadeService;
    }

    @PostMapping
    public ResponseEntity<Oportunidade> criarOportunidade(@Valid @RequestBody OportunidadeDTO oportunidadeDTO) {
        Oportunidade oportunidade = oportunidadeService.criarOportunidade(oportunidadeDTO);
        oportunidadeService.distribuirOportunidade(oportunidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(oportunidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oportunidade> atualizarOportunidade(@PathVariable Long id, @Valid @RequestBody OportunidadeDTO oportunidadeDTO) {
        Oportunidade updatedOportunidade = oportunidadeService.atualizarOportunidade(id, oportunidadeDTO);
        if (updatedOportunidade != null) {
            return ResponseEntity.ok(updatedOportunidade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOportunidade(@PathVariable Long id) {
        if (oportunidadeService.existeOportunidade(id)) {
            oportunidadeService.deletarOportunidade(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscarOportunidadePorId(@PathVariable Long id) {
        Optional<Oportunidade> oportunidade = oportunidadeService.getOportunidadeById(id);
        return oportunidade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Oportunidade>> listarOportunidades() {
        List<Oportunidade> oportunidades = oportunidadeService.listarOportunidades();
        return ResponseEntity.ok(oportunidades);
    }
}
