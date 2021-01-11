package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;

public interface OrdemServicoRepositoryQuery
{
    Page<OrdemServico> pesquisar( OrdemServicoFilter filtro, Pageable pageable );
}
