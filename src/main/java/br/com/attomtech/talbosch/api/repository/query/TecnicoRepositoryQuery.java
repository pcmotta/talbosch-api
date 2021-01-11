package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;

public interface TecnicoRepositoryQuery
{
    Page<Tecnico> pesquisar( TecnicoFilter filtro, Pageable pageable );
}
