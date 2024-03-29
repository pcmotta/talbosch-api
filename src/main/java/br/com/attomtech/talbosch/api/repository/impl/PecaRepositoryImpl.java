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

import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.repository.filter.PecaFilter;
import br.com.attomtech.talbosch.api.repository.query.PecaRepositoryQuery;

public class PecaRepositoryImpl extends RepositoryImpl<PecaFilter, Peca> implements PecaRepositoryQuery
{
    @Override
    public Page<Peca> pesquisar( PecaFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, PecaFilter.CODIGO );
    }

    @Override
    protected Predicate[] filtrar( PecaFilter filtro, CriteriaBuilder builder, Root<Peca> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( StringUtils.hasText( filtro.getCodigo( ) ) )
            predicates.add( builder.like( builder.lower( from.get( PecaFilter.CODIGO ) ), "%" + filtro.getCodigo( ).toLowerCase( ) + "%" ) );
        
        if( StringUtils.hasText( filtro.getDescricao( ) ) )
            predicates.add( builder.like( builder.lower( from.get( PecaFilter.DESCRICAO ) ), "%" + filtro.getDescricao( ).toLowerCase( ) + "%" ) );
        
        if( filtro.getValorDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( PecaFilter.VALOR ), filtro.getValorDe( ) ) );
        
        if( filtro.getValorAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( PecaFilter.VALOR ), filtro.getValorAte( ) ) );
        
        if( filtro.getValorTecnicoDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( PecaFilter.VALORTECNICO ), filtro.getValorTecnicoDe( ) ) );
        
        if( filtro.getValorTecnicoAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( PecaFilter.VALORTECNICO ), filtro.getValorTecnicoAte( ) ) );
        
        if( filtro.getValorMaoDeObraDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( PecaFilter.VALORMAODEOBRA ), filtro.getValorMaoDeObraDe( ) ) );
        
        if( filtro.getValorMaoDeObraAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( PecaFilter.VALORMAODEOBRA ), filtro.getValorMaoDeObraAte( ) ) );
        
        if( StringUtils.hasText( filtro.getModelo( ) ) )
        {
            Join<Object, Object> modelo = from.join( PecaFilter.MODELO );
            predicates.add( builder.like( builder.lower( modelo.get( PecaFilter.MODELOID ).get( PecaFilter.MODELODESCRICAO ) ), 
                    "%" + filtro.getModelo( ).toLowerCase( ) + "%" ) );
        }
        
        if( filtro.getAparelho( ) != null )
            predicates.add( builder.equal( from.get( PecaFilter.APARELHO ), filtro.getAparelho( ) ) );
        
        if( filtro.getFabricante( ) != null )
            predicates.add( builder.equal( from.get( PecaFilter.FABRICANTE ), filtro.getFabricante( ) ) );
        
        if( filtro.getAtivo( ) != null )
            predicates.add( builder.equal( from.get( PecaFilter.ATIVO ), filtro.getAtivo( ) ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}