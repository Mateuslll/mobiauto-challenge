package com.mobiauto.service;

import com.mobiauto.dto.OportunidadeDTO;
import com.mobiauto.mapper.OportunidadeMapper;
import com.mobiauto.model.Oportunidade;
import com.mobiauto.model.Usuario;
import com.mobiauto.repository.OportunidadeRepository;
import com.mobiauto.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    public Oportunidade distribuirOportunidade(Oportunidade oportunidade) {
        List<Usuario> assistentes = usuarioRepository.findByCargo("ASSISTENTE");
        Usuario assistenteEscolhido = assistentes.stream()
                .min(Comparator.comparingLong(this::getQuantidadeOportunidadesEmAndamento)
                        .thenComparing(this::getUltimaDataAtribuicao))
                .orElseThrow(() -> new RuntimeException("Nenhum assistente disponível"));

        oportunidade.setResponsavel(assistenteEscolhido);
        oportunidade.setDataAtribuicao(LocalDateTime.now());
        return oportunidadeRepository.save(oportunidade);
    }

    private long getQuantidadeOportunidadesEmAndamento(Usuario assistente) {
        return oportunidadeRepository.countByResponsavelAndStatus(assistente, "EM_ANDAMENTO");
    }

    private LocalDateTime getUltimaDataAtribuicao(Usuario assistente) {
        return oportunidadeRepository.findTopByResponsavelOrderByDataAtribuicaoDesc(assistente)
                .map(Oportunidade::getDataAtribuicao)
                .orElse(LocalDateTime.MIN);
    }

    public Oportunidade transferirOportunidade(Long idOportunidade, Long idNovoAssistente) {
        Optional<Oportunidade> oportunidadeOpt = oportunidadeRepository.findById(idOportunidade);
        Optional<Usuario> novoAssistenteOpt = usuarioRepository.findById(idNovoAssistente);

        if (oportunidadeOpt.isPresent() && novoAssistenteOpt.isPresent()) {
            Oportunidade oportunidade = oportunidadeOpt.get();
            Usuario novoAssistente = novoAssistenteOpt.get();
            oportunidade.setResponsavel(novoAssistente);
            oportunidade.setDataAtribuicao(LocalDateTime.now());
            return oportunidadeRepository.save(oportunidade);
        } else {
            throw new RuntimeException("Oportunidade ou Assistente não encontrado");
        }
    }

    public Oportunidade editarOportunidade(Long idOportunidade, OportunidadeDTO oportunidadeDTO, Usuario usuario) throws AccessDeniedException {
        Optional<Oportunidade> oportunidadeOpt = oportunidadeRepository.findById(idOportunidade);

        if (oportunidadeOpt.isPresent()) {
            Oportunidade oportunidade = oportunidadeOpt.get();

            if (usuario.getCargo().equals("ADMIN") || usuario.getCargo().equals("PROPRIETARIO") ||
                    oportunidade.getResponsavel().equals(usuario)) {
                BeanUtils.copyProperties(oportunidadeDTO, oportunidade);
                oportunidade.setDataConclusao(LocalDateTime.now()); // ou outra lógica para data de conclusão
                return oportunidadeRepository.save(oportunidade);
            } else {
                throw new org.springframework.security.access.AccessDeniedException("Usuário não autorizado a editar esta oportunidade");
            }
        } else {
            throw new EntityNotFoundException("Oportunidade não encontrada");
        }
    }
    public boolean existeOportunidade(Long id) {
        return oportunidadeRepository.existsById(id);
    }
}
