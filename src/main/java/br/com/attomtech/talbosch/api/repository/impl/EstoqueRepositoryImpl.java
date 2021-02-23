package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.dto.EstoquePesquisaDTO;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;
import br.com.attomtech.talbosch.api.reports.EstoqueTecnicoReport;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;
import br.com.attomtech.talbosch.api.repository.query.EstoqueRepositoryQuery;

public class EstoqueRepositoryImpl extends RepositoryImplDto<EstoqueFilter, Estoque, EstoquePesquisaDTO> implements EstoqueRepositoryQuery
{
    @Override
    public Page<EstoquePesquisaDTO> pesquisar( EstoqueFilter filtro, Pageable pageable )
    {
        return pesquisarDadosDto( filtro, pageable, "agendadoPara", false );
    }
    
    @Override
    protected void select( CriteriaBuilder builder, CriteriaQuery<EstoquePesquisaDTO> query, Root<Estoque> from )
    {
        Join<Object, Object> peca = from.join( EstoqueFilter.PECA );
        Join<Object, Object> cliente = from.join( EstoqueFilter.CLIENTE );
        Join<Object, Object> ordemServico = from.join( EstoqueFilter.OS );
        
        query.select( builder.construct( EstoquePesquisaDTO.class, from.get( "codigo" ),
                peca.get( "codigo" ), peca.get( "descricao" ), cliente.get( "nome" ), ordemServico.get( "numero" ),
                from.get( "agendadoPara" ), from.get( "tipo" ), from.get( "status" ) ) );
    }

    @Override
    protected Predicate[] filtrar( EstoqueFilter filtro, CriteriaBuilder builder, Root<Estoque> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( StringUtils.hasText( filtro.getPeca( ) ) )
        {
            Join<Object, Object> peca = from.join( EstoqueFilter.PECA );
            predicates.add( builder.equal( peca.get( EstoqueFilter.PECACODIGO ), filtro.getPeca( ) ) );
        }
        
        if( StringUtils.hasText( filtro.getCliente( ) ) )
        {
            Join<Object, Object> cliente = from.join( EstoqueFilter.CLIENTE );
            predicates.add( builder.like( cliente.get( EstoqueFilter.CLIENTENOME ), "%" + filtro.getCliente( ) + "%" ) );
        }
        
        if( filtro.getOrdemServico( ) != null )
        {
            Join<Object, Object> ordemServico = from.join( EstoqueFilter.OS );
            predicates.add( builder.equal( ordemServico.get( EstoqueFilter.OSNUMERO ), filtro.getOrdemServico( ) ) );
        }
        
        if( filtro.getTecnico( ) != null )
        {
            Join<Object, Object> tecnico = from.join( EstoqueFilter.TECNICO );
            predicates.add( builder.equal( tecnico.get( EstoqueFilter.TECNICOCODIGO ), filtro.getTecnico( ) ) );
        }
        
        if( filtro.getTipo( ) != null )
            predicates.add( builder.equal( from.get( EstoqueFilter.TIPO ), filtro.getTipo( ) ) );
        
        if( filtro.getStatus( ) != null )
            predicates.add( builder.equal( from.get( EstoqueFilter.STATUS ), filtro.getStatus( ) ) );
        
        if( filtro.getAgendadoParaDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( EstoqueFilter.AGENDADOPARA ), filtro.getAgendadoParaDe( ) ) );
        
        if( filtro.getAgendadoParaAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( EstoqueFilter.AGENDADOPARA ), filtro.getAgendadoParaAte( ) ) );
        
        if( isNotNull( filtro.getPedido( ) ) )
        {
            Join<Object, Object> pedido = from.join( EstoqueFilter.PEDIDO );
            predicates.add( builder.equal( pedido.get( EstoqueFilter.PEDIDO ), filtro.getPedido( ) ) );
        }
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }

    @Override
    public List<Estoque> buscarEstoqueTecnico( EstoqueTecnicoReport filtro )
    {
        CriteriaBuilder builder = getCriteriaBuilder( );
        CriteriaQuery<Estoque> query = getCriteriaQuery( builder );
        Root<Estoque> from = getRoot( query );
        
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        predicates.add( builder.equal( from.get( "agendadoPara" ), filtro.getData( ) ) );
        
        List<TipoEstoque> tipos = new ArrayList<>( );
        
        if( filtro.isTodos( ) )
            tipos.addAll( Arrays.asList( TipoEstoque.values( ) ) );
        else
        {
            if( filtro.isGarantia( ) )
                tipos.add( TipoEstoque.GARANTIA );
            
            if( filtro.isForaGarantia( ) )
                tipos.add( TipoEstoque.FORAGARANTIA );
            
            if( filtro.isBuffer( ) )
                tipos.add( TipoEstoque.BUFFER );
        }
        
        In<TipoEstoque> tipoIn = builder.in( from.get( "tipo" ) );
        tipos.forEach( t -> tipoIn.value( t ) );
        
        predicates.add( tipoIn );
        
        if( filtro.getTecnico( ) != null )
            predicates.add( builder.equal( from.get( "tecnico" ), filtro.getTecnico( ) ) );
        else
            predicates.add( builder.isNotNull( from.get( "tecnico" ) ) );
        
        query.where( predicates.toArray( new Predicate[predicates.size( )] ) );
        TypedQuery<Estoque> typed = manager.createQuery( query );
        
        return typed.getResultList( );
    }
}
