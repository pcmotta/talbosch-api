package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;
import br.com.attomtech.talbosch.api.repository.query.EstoqueRepositoryQuery;

public class EstoqueRepositoryImpl extends RepositoryImpl<EstoqueFilter, Estoque> implements EstoqueRepositoryQuery
{
    @Override
    public Page<Estoque> pesquisar( EstoqueFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable );
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
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}
