package br.com.attomtech.talbosch.api.repository.impl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.repository.RepositoryImplSuper;

public abstract class RepositoryImplDto<T, X extends Model, D extends Model> extends RepositoryImplSuper<T, X, D>
{
    protected Page<D> pesquisarDadosDto( T filtro, Pageable pageable )
    {
        return pesquisarDadosDto( filtro, pageable, null );
    }
    
    protected Page<D> pesquisarDadosDto( T filtro, Pageable pageable, String order )
    {
        return pesquisarDadosDto( filtro, pageable, order, true );
    }
    
    protected Page<D> pesquisarDadosDto( T filtro, Pageable pageable, String order, boolean isAsc )
    {
        CriteriaBuilder  builder = manager.getCriteriaBuilder( );
        CriteriaQuery<D> query   = builder.createQuery( classeDto );
        Root<X>          from    = query.from( classe );
        
        query.select( select( builder, from ) );
        query.where( filtrar( filtro, builder, from ) );
        adicionarOrdenacao( pageable, builder, query, from, order, isAsc );
        
        TypedQuery<D> typed = manager.createQuery( query );
        adicionarPaginacao( typed, pageable );
        
        return new PageImpl<>( typed.getResultList( ), pageable, total( filtro ) );
    }
    
    protected abstract CompoundSelection<D> select( CriteriaBuilder builder, Root<X> from );
}
