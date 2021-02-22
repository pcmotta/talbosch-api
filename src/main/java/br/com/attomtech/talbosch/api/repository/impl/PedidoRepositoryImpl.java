package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.filter.PedidoFilter;
import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.repository.query.PedidoRepositoryQuery;

public class PedidoRepositoryImpl extends RepositoryImpl<PedidoFilter, Pedido> implements PedidoRepositoryQuery
{
    @Override
    public Page<Pedido> pesquisar( PedidoFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, "data", false );
    }
    
    @Override
    protected Predicate[] filtrar( PedidoFilter filtro, CriteriaBuilder builder, Root<Pedido> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( isNotNull( filtro.getCodigo( ) ) )
            predicates.add( builder.equal( from.get( PedidoFilter.CODIGO ), filtro.getCodigo( ) ) );
        
        if( hasText( filtro.getPedido( ) ) )
            predicates.add( builder.equal( builder.lower( from.get( PedidoFilter.PEDIDO ) ), filtro.getPedido( ).toLowerCase( ) ) );
       
        if( hasText( filtro.getNotaFiscal( ) ) )
            predicates.add( builder.equal( builder.lower( from.get( PedidoFilter.NOTAFISCAL ) ), filtro.getNotaFiscal( ).toLowerCase( ) ) );
        
        if( isNotNull( filtro.getDataDe( ) ) )
            predicates.add( builder.greaterThanOrEqualTo( from.get( PedidoFilter.DATA ), filtro.getDataDe( ) ) );
        
        if( isNotNull( filtro.getDataAte( ) ) )
            predicates.add( builder.lessThanOrEqualTo( from.get( PedidoFilter.DATA ), filtro.getDataAte( ) ) );
        
        if( isNotNull( filtro.getPedidoPor( ) ) )
            predicates.add( builder.equal( from.get( PedidoFilter.PEDIDOPOR ), filtro.getPedidoPor( ) ) );
        
        if( isNotNull( filtro.getStatus( ) ) )
            predicates.add( builder.equal( from.get( PedidoFilter.STATUS ), filtro.getStatus( ) ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}