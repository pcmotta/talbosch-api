package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.dto.pesquisa.PecaPesquisaDTO;
import br.com.attomtech.talbosch.api.repository.filter.PecaFilter;

public interface PecaRepositoryQuery
{
    Page<PecaPesquisaDTO> pesquisar( PecaFilter filter, Pageable pageable );
}
