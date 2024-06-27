package com.mobiauto.mapper;

import com.mobiauto.dto.OportunidadeDTO;
import com.mobiauto.model.Oportunidade;
import com.mobiauto.model.Usuario;
import com.mobiauto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OportunidadeMapper {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public OportunidadeMapper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Oportunidade toEntity(OportunidadeDTO dto) {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setCodigo(dto.codigo());
        oportunidade.setStatus(dto.status());
        oportunidade.setMotivoConclusao(dto.motivoConclusao());
        oportunidade.setNomeCliente(dto.clienteNome());
        oportunidade.setEmailCliente(dto.clienteEmail());
        oportunidade.setTelefoneCliente(dto.clienteTelefone());
        oportunidade.setMarcaVeiculo(dto.veiculoMarca());
        oportunidade.setModeloVeiculo(dto.veiculoModelo());
        oportunidade.setVersaoVeiculo(dto.veiculoVersao());
        oportunidade.setAnoModeloVeiculo(dto.veiculoAnoModelo());

        if (dto.responsavelId() != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(dto.responsavelId());
            usuarioOptional.ifPresent(oportunidade::setResponsavel);
        }

        return oportunidade;
    }

    // Método para converter de Entidade para DTO
    public OportunidadeDTO toDTO(Oportunidade oportunidade) {
        Long responsavelId = oportunidade.getResponsavel() != null ? oportunidade.getResponsavel().getId() : null;

        return new OportunidadeDTO(
                oportunidade.getCodigo(),
                oportunidade.getStatus(),
                oportunidade.getMotivoConclusao(),
                oportunidade.getNomeCliente(),
                oportunidade.getEmailCliente(),
                oportunidade.getTelefoneCliente(),
                oportunidade.getMarcaVeiculo(),
                oportunidade.getModeloVeiculo(),
                oportunidade.getVersaoVeiculo(),
                oportunidade.getAnoModeloVeiculo(),
                responsavelId
        );
    }
}
