package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}
