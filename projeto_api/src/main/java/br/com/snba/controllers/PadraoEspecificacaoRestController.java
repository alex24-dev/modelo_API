package br.com.snba.controllers;

import br.com.snba.models.dtos.padraoespecificacao.AtualizarPadraoEspecificacaoDTO;
import br.com.snba.models.dtos.padraoespecificacao.PadraoEspecificacaoDTO;
import br.com.snba.models.dtos.padraoespecificacao.PadraoEspecificacaoInserirDTO;
import br.com.snba.models.dtos.pagination.PaginationRequestDTO;
import br.com.snba.models.dtos.pagination.PaginationResponseDTO;
import br.com.snba.services.PadraoEspecificacaoService;
import br.jus.pdpj.commons.annotations.PdpjApiResponses;
import br.jus.pdpj.starter.base.ApiVersions;
import br.jus.pdpj.starter.exceptions.BusinessException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@PdpjApiResponses
@RequiredArgsConstructor
@RequestMapping(ApiVersions.V1 + "/padroes-especificacoes")
public class PadraoEspecificacaoRestController {

    private final PadraoEspecificacaoService padraoEspecificacaoService;

    @PostMapping
    @ApiOperation(value = "Incluir um novo padrão especificação.", notes = "Padrão Especificação..", response = PadraoEspecificacaoDTO.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    public ResponseEntity<PadraoEspecificacaoDTO> criar(@Valid @RequestBody PadraoEspecificacaoInserirDTO dto) throws BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(padraoEspecificacaoService.criar(dto));
    }

    @ApiOperation(value = "Busca padrão especificação por id", notes = "Busca por id.", response = PadraoEspecificacaoDTO.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    @GetMapping("/{id}")
    public ResponseEntity<PadraoEspecificacaoDTO> buscar(final @PathVariable Long id) {
        return ResponseEntity.ok(padraoEspecificacaoService.buscarPor(id));
    }

    @GetMapping
    @ApiOperation(value = "Listar Padrão Especificação", notes = "Padrão.", response = PadraoEspecificacaoDTO.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    public ResponseEntity<PaginationResponseDTO<PadraoEspecificacaoDTO>> listar(@RequestParam(value = "query", required = false) String query,
                                                                                @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
                                                                                @RequestParam(value = "pageindex", required = false, defaultValue = "0") Integer pageIndex,
                                                                                @RequestParam(value = "sort", required = false) String sort) {

        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(query, pagesize, pageIndex, sort);

        return ResponseEntity.ok().body(padraoEspecificacaoService.listar(paginationRequestDTO));
    }

    @PostMapping("/{id}/ativar")
    @ApiOperation(
            value = "Ativar padrão especificação.",
            notes = "Altera o status do padrão especificação para o status Ativo.")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    public ResponseEntity<Void> ativar(final @PathVariable Long id) {
        padraoEspecificacaoService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/desativar")
    @ApiOperation(value = "Desativa um padrão especificação", notes = "Altera o status da padrão para o status Desativado.")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    public ResponseEntity<Void> desativar(final @PathVariable Long id) {
        padraoEspecificacaoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    @ApiOperation(
            value = "Deleta um padrão especificação.",
            notes = "Deleta um padrão especificação pelo id .", response = PadraoEspecificacaoDTO.class)
    public ResponseEntity<Void> remover(@PathVariable Long id) throws BusinessException {
        padraoEspecificacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "atualiza  padrão especificação", notes = "Atualiza padrao especificação.")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", value = "Authorization JWT (Bearer ...)", paramType = "header"))
    public ResponseEntity<Void> atualizar(final @PathVariable Long id,
                                          @Valid final @RequestBody AtualizarPadraoEspecificacaoDTO atualizarPadraoEspecificacaoDTO) throws BusinessException {
        padraoEspecificacaoService.atualizar(id, atualizarPadraoEspecificacaoDTO);
        return ResponseEntity.noContent().build();
    }
}
