package br.com.attomtech.talbosch.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.repository.query.EstoqueRepositoryQuery;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>, EstoqueRepositoryQuery
{
    Optional<List<Estoque>> findByOrdemServicoNumero( Long numero );
    
    @Query("SELECT e FROM Estoque e WHERE e.pedido = ?1 AND e.status != 'NAOBUSCADA'")
    List<Estoque> buscarPorPedido( Pedido pedido );
    
    @Query("SELECT COUNT(e) FROM Estoque e WHERE e.status = ?1")
    Long pecasPorStatus( StatusEstoque status );
    
    @Query("SELECT e FROM Estoque e WHERE e.ordemServico.numero = ?1")
    List<Estoque> buscarPorOrdem( Long ordemDeServico );
    
    @Query("SELECT COUNT(e) FROM Estoque e WHERE e.ordemServico IS NULL AND e.cliente IS NULL AND e.status = 'EMESTOQUE'")
    Long buscarSemClienteEOrdemDeServico( );
    
    @Query("SELECT COUNT(e) FROM Estoque e WHERE ( e.ordemServico IS NOT NULL OR e.cliente IS NOT NULL ) AND e.agendadoPara IS NULL")
    Long buscarNaoAgendadas( );
}
