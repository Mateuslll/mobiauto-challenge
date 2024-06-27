package com.mobiauto.controller;

import com.mobiauto.dto.VeiculoDTO;
import com.mobiauto.model.Veiculo;
import com.mobiauto.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Autowired
    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Veiculo> criarVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        BeanUtils.copyProperties(veiculoDTO, veiculo);
        Veiculo veiculoSalvo = veiculoService.criarVeiculo(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable Long id, @Valid @RequestBody VeiculoDTO veiculoDTO) {
        Optional<Veiculo> existingVeiculo = veiculoService.getVeiculoById(id);
        if (existingVeiculo.isPresent()) {
            Veiculo veiculo = existingVeiculo.get();
            BeanUtils.copyProperties(veiculoDTO, veiculo);
            veiculo.setId(id);
            Veiculo updatedVeiculo = veiculoService.atualizarVeiculo(veiculo);
            return ResponseEntity.ok(updatedVeiculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        if (veiculoService.existeVeiculo(id)) {
            veiculoService.deletarVeiculo(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarVeiculoPorId(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        return veiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarVeiculos();
        return ResponseEntity.ok(veiculos);
    }
}
