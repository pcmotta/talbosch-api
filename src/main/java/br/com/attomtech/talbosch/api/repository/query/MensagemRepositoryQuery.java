package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.repository.filter.MensagemFilter;

public interface MensagemRepositoryQuery
{
    Page<Mensagem> pesquisar( MensagemFilter filtro, Pageable pageable );
}
