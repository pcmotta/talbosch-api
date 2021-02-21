package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.repository.query.PedidoRepositoryQuery;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoRepositoryQuery
{

}
