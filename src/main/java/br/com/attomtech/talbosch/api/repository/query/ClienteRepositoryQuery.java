package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery
{
    Page<Cliente> pesquisar( ClienteFilter filtro, Pageable pageable );
}
