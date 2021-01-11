package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;

public interface EstoqueRepositoryQuery
{
    Page<Estoque> pesquisar( EstoqueFilter filtro, Pageable pageable );
}
