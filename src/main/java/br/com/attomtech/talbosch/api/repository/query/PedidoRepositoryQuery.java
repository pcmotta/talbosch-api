package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.filter.PedidoFilter;
import br.com.attomtech.talbosch.api.model.Pedido;

public interface PedidoRepositoryQuery
{
    Page<Pedido> pesquisar( PedidoFilter filtro, Pageable pageable );
}
