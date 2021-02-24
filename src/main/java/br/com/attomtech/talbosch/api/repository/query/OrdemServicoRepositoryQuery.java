package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.dto.pesquisa.OrdemServicoPesquisaDTO;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;

public interface OrdemServicoRepositoryQuery
{
    Page<OrdemServicoPesquisaDTO> pesquisar( OrdemServicoFilter filtro, Pageable pageable );
}
