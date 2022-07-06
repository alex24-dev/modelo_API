package br.com.snba.repositories;


import br.com.snba.models.entities.PadraoEspecificacao;
import br.com.snba.models.entities.SubClasse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PadraoEspecificacaoRepository extends BaseRepository<PadraoEspecificacao,Long> {

    Optional<PadraoEspecificacao> findByIdAndExcluido_False(Long id);

    Optional<PadraoEspecificacao> findByNomeIgnoreCaseAndExcluido_False(String nome);

    List<PadraoEspecificacao> findBySubClasseAndExcluido_False(SubClasse subClasse);
}
