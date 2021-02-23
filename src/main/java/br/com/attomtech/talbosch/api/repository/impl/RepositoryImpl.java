package br.com.attomtech.talbosch.api.repository.impl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.repository.RepositoryImplSuper;

public abstract class RepositoryImpl<T, X extends Model> extends RepositoryImplSuper<T, X, X>
{
    protected Page<X> pesquisarDados( T filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, null );
    }
    
    protected Page<X> pesquisarDados( T filtro, Pageable pageable, String order )
    {
        return pesquisarDados( filtro, pageable, order, true );
    }
    
    protected Page<X> pesquisarDados( T filtro, Pageable pageable, String order, boolean isAsc )
    {
        CriteriaBuilder  builder = manager.getCriteriaBuilder( );
        CriteriaQuery<X> query   = builder.createQuery( classe );
        Root<X>          from    = query.from( classe );
        
        query.where( filtrar( filtro, builder, from ) );
        adicionarOrdenacao( pageable, builder, query, from, order, isAsc );
        
        TypedQuery<X> typed = manager.createQuery( query );
        adicionarPaginacao( typed, pageable );
        
        return new PageImpl<>( typed.getResultList( ), pageable, total( filtro ) );
    }
}
