package com.mobiauto.service;

import com.mobiauto.dto.OportunidadeDTO;
import com.mobiauto.mapper.OportunidadeMapper;
import com.mobiauto.model.Oportunidade;
import com.mobiauto.model.Usuario;
import com.mobiauto.repository.OportunidadeRepository;
import com.mobiauto.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final OportunidadeMapper oportunidadeMapper;

    @Autowired
    public OportunidadeService(OportunidadeRepository oportunidadeRepository, UsuarioRepository usuarioRepository, OportunidadeMapper oportunidadeMapper) {
        this.oportunidadeRepository = oportunidadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.oportunidadeMapper = oportunidadeMapper;
    }

    public Oportunidade criarOportunidade(OportunidadeDTO oportunidadeDTO) {
        Oportunidade oportunidade = oportunidadeMapper.toEntity(oportunidadeDTO);
        return oportunidadeRepository.save(oportunidade);
    }

    public Oportunidade atualizarOportunidade(Long id, OportunidadeDTO oportunidadeDTO) {
        Optional<Oportunidade> optionalOportunidade = oportunidadeRepository.findById(id);
        if (optionalOportunidade.isPresent()) {
            Oportunidade oportunidade = optionalOportunidade.get();
            BeanUtils.copyProperties(oportunidadeDTO, oportunidade);
            oportunidade.setId(id);
            return oportunidadeRepository.save(oportunidade);
        }
        return null;
    }

    public void deletarOportunidade(Long id) {
        oportunidadeRepository.deleteById(id);
    }

    public Optional<Oportunidade> getOportunidadeById(Long id) {
        return oportunidadeRepository.findById(id);
    }

    public List<Oportunidade> listarOportunidades() {
        return oportunidadeRepository.findAll();
    }

    public void distribuirOportunidade(Oportunidade oportunidade) {
        List<Usuario> assistentes = usuarioRepository.findAssistentesDisponiveis();
        if (!assistentes.isEmpty()) {
            Usuario responsavel = assistentes.get(0);
            oportunidade.setResponsavel(responsavel);
            oportunidade.setDataAtribuicao(LocalDateTime.now());
            oportunidadeRepository.save(oportunidade);

            responsavel.setUltimaOportunidadeRecebida(LocalDateTime.now());
            usuarioRepository.save(responsavel);
        }
    }

    public boolean existeOportunidade(Long id) {
        return oportunidadeRepository.existsById(id);
    }
}
