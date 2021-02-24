package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.dto.pesquisa.PedidoPesquisaDTO;
import br.com.attomtech.talbosch.api.filter.PedidoFilter;

public interface PedidoRepositoryQuery
{
    Page<PedidoPesquisaDTO> pesquisar( PedidoFilter filtro, Pageable pageable );
}
