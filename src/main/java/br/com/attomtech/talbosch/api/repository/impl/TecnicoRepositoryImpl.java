package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;
import br.com.attomtech.talbosch.api.repository.query.TecnicoRepositoryQuery;

public class TecnicoRepositoryImpl extends RepositoryImpl<TecnicoFilter, Tecnico> implements TecnicoRepositoryQuery
{
    @Override
    public Page<Tecnico> pesquisar( TecnicoFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable );
    }

    @Override
    protected Predicate[] filtrar( TecnicoFilter filtro, CriteriaBuilder builder, Root<Tecnico> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( StringUtils.hasText( filtro.getNome( ) ) )
            predicates.add( builder.like( builder.lower( from.get( TecnicoFilter.NOME ) ), "%" + filtro.getNome( ).toLowerCase( ) + "%" ) );
        
        if( filtro.getAtivo( ) != null )
            predicates.add( builder.equal( from.get( TecnicoFilter.ATIVO ), filtro.getAtivo( ) ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }

}
