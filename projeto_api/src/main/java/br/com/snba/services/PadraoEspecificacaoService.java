package br.com.snba.services;


import br.com.snba.models.dtos.padraoespecificacao.AtualizarPadraoEspecificacaoDTO;
import br.com.snba.models.dtos.padraoespecificacao.PadraoEspecificacaoDTO;
import br.com.snba.models.dtos.padraoespecificacao.PadraoEspecificacaoInserirDTO;
import br.com.snba.models.dtos.pagination.PaginationRequestDTO;
import br.com.snba.models.dtos.pagination.PaginationResponseDTO;
import br.com.snba.models.entities.Bem;
import br.com.snba.models.entities.PadraoEspecificacao;
import br.com.snba.models.entities.Situacao;
import br.com.snba.models.enums.TipoOperacaoEnum;
import br.com.snba.models.mappers.PadraoEspecificacaoFunctionMapper;
import br.com.snba.models.mappers.PadraoEspecificacaoMapper;
import br.com.snba.models.mappers.PaginationMapper;
import br.com.snba.repositories.BaseRepository;
import br.com.snba.repositories.BemRepository;
import br.com.snba.repositories.PadraoEspecificacaoRepository;
import br.jus.pdpj.starter.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.snba.models.enums.message.PadraoEspecificacaoMessage.PADRAO_ESPECIFICACAO_JA_EXISTE;
import static br.com.snba.models.enums.message.PadraoEspecificacaoMessage.PADRAO_ESPECIFICACAO_NAOENCONTRADA;
import static br.com.snba.models.enums.message.PadraoEspecificacaoMessage.PADRAO_ESPECIFICACAO_RELACIONADO_COM_BEM;


@Service
@RequiredArgsConstructor
public class PadraoEspecificacaoService extends BaseService<PadraoEspecificacao> {

    private final PadraoEspecificacaoRepository padraoEspecificacaoRepository;
    private final BemRepository bemRepository;
    private final PadraoEspecificacaoMapper padraoEspecificacaoMapper;
    private final PaginationMapper<PadraoEspecificacaoDTO> paginationMapper;
    private final SituacaoService situacaoService;
    private final SubClasseService subClasseService;
    private final PadraoEspecificacaoFunctionMapper padraoEspecificacaoFunctionMapper;
    private final AtributoEspecificacaoService atributoEspecificacaoService;

    public PadraoEspecificacaoDTO criar(final PadraoEspecificacaoInserirDTO dto) throws BusinessException {
        validarExisteNomePadrao(dto.getNome());
        var situacao = situacaoService.buscarSituacaoEmElaboracao();
        var subClasse = subClasseService.buscarSubClassePorId(dto.getSubclasseId());
        var padraoEspecificacao = save(padraoEspecificacaoMapper.toEntity(dto, false, situacao, subClasse), TipoOperacaoEnum.CRIAR);

        return padraoEspecificacaoFunctionMapper.apply(padraoEspecificacao);
    }

    public PadraoEspecificacaoDTO atualizar(final Long id, final AtualizarPadraoEspecificacaoDTO dto) throws BusinessException {
        validarExisteNomePadrao(dto.getNome(), id);
        var padrao = buscar(id);
        var subClasse = subClasseService.buscarSubClassePorId(dto.getSubclasseId());
        padrao.setNome(dto.getNome());
        padrao.setSubClasse(subClasse);

        return padraoEspecificacaoFunctionMapper.apply(save(padrao, TipoOperacaoEnum.ATUALIZAR));
    }


    public PaginationResponseDTO<PadraoEspecificacaoDTO> listar(final PaginationRequestDTO paginationRequestDTO) {
        final Page<PadraoEspecificacaoDTO> padrao = findByPagination(paginationRequestDTO)
                .map(padraoEspecificacaoFunctionMapper);

        return paginationMapper.toDTO(padrao);
    }

    public void ativar(final Long id) {
        var situacao = situacaoService.buscarSituacaoAtivado();
        alterarSituacaoPadraoEspecificacao(id, situacao);
    }

    public void desativar(final Long id) {
        var situacao = situacaoService.buscarSituacaoDesativado();
        alterarSituacaoPadraoEspecificacao(id, situacao);
    }

    @Transactional
    public void deletar(final Long id) throws BusinessException {
        var padraoEspecificacao = buscar(id);
        validarExisteRelacionamentoBem(padraoEspecificacao);
        deletarAtributosEspecificacao(padraoEspecificacao);

        padraoEspecificacao.setExcluido(Boolean.TRUE);
        padraoEspecificacao.setAtributos(Collections.emptyList());
        save(padraoEspecificacao, TipoOperacaoEnum.DELETAR);
    }

    private void validarExisteRelacionamentoBem(PadraoEspecificacao padraoEspecificacao) throws BusinessException {
        var bens = bemRepository.findByPadraoEspecificacaoIdAndExcluido_False(padraoEspecificacao.getId());
        if (!CollectionUtils.isEmpty(bens)) {
            var codigosBens = bens.stream()
                    .map(Bem::getId)
                    .collect(Collectors.toList())
                    .toString();
            throw new BusinessException(PADRAO_ESPECIFICACAO_RELACIONADO_COM_BEM.getMensagem(padraoEspecificacao.getNome(), codigosBens));
        }
    }

    public PadraoEspecificacaoDTO buscarPor(final Long id) {
        var padraoEspecificacao = buscar(id);
        return padraoEspecificacaoFunctionMapper.apply(padraoEspecificacao);
    }

    private PadraoEspecificacao buscar(final Long id) {
        return padraoEspecificacaoRepository.findByIdAndExcluido_False(id)
                .orElseThrow(() -> new EntityNotFoundException(PADRAO_ESPECIFICACAO_NAOENCONTRADA.getMensagem(id)));
    }

    private void deletarAtributosEspecificacao(PadraoEspecificacao padraoEspecificacao) throws BusinessException {
        if (padraoEspecificacao != null && !CollectionUtils.isEmpty(padraoEspecificacao.getAtributos())) {
            for (var atributo : padraoEspecificacao.getAtributos()) {
                atributoEspecificacaoService.deletar(atributo.getId());
            }
        }
    }

    private void validarExisteNomePadrao(final String nome) throws BusinessException {
        var padrao = padraoEspecificacaoRepository.findByNomeIgnoreCaseAndExcluido_False(nome);
        if (padrao.isPresent()) {
            throw new BusinessException(PADRAO_ESPECIFICACAO_JA_EXISTE.getMensagem(nome));
        }
    }

    private void validarExisteNomePadrao(final String nome, final Long padraoEspecificacaoId) throws BusinessException {
        var padrao = padraoEspecificacaoRepository.findByNomeIgnoreCaseAndExcluido_False(nome);
        if (padrao.isPresent() && !Objects.equals(padraoEspecificacaoId, padrao.get().getId())) {
            throw new BusinessException(PADRAO_ESPECIFICACAO_JA_EXISTE.getMensagem(nome));
        }
    }

    private void alterarSituacaoPadraoEspecificacao(final Long id, final Situacao situacao) {
        var padraoEspecificacao = buscar(id);
        padraoEspecificacao.setSituacao(situacao);
        save(padraoEspecificacao, TipoOperacaoEnum.ATUALIZAR);
    }

    @Override
    protected BaseRepository<PadraoEspecificacao, Long> getRepository() {
        return padraoEspecificacaoRepository;
    }
}
