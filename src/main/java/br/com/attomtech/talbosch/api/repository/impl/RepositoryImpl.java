package br.com.attomtech.talbosch.api.repository.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.repository.paginacao.PaginacaoOrdenacao;

public abstract class RepositoryImpl<T,X> extends PaginacaoOrdenacao<X>
{
    @PersistenceContext
    protected EntityManager manager;
    private Class<X> classe;
    
    @SuppressWarnings("unchecked")
    protected RepositoryImpl( )
    {
        ParameterizedType type = (ParameterizedType)getClass( ).getGenericSuperclass( );
        this.classe = (Class<X>)type.getActualTypeArguments( )[1];
    }
    
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
    
    protected Long total( T filtro )
    {
        CriteriaBuilder     builder = manager.getCriteriaBuilder( );
        CriteriaQuery<Long> query   = builder.createQuery( Long.class );
        Root<X>             from    = query.from( classe );
        
        query.where( filtrar( filtro, builder, from ) );
        query.select( builder.count( from ) );
        
        return manager.createQuery( query ).getSingleResult( );
    }
    
    protected boolean hasText( String text )
    {
        return StringUtils.hasText( text );
    }
    
    protected boolean isNotNull( Object obj )
    {
        return obj != null;
    }
    
    protected CriteriaBuilder getCriteriaBuilder( )
    {
        return manager.getCriteriaBuilder( );
    }
    
    protected CriteriaQuery<X> getCriteriaQuery( CriteriaBuilder builder )
    {
        return builder.createQuery( classe );
    }
    
    protected Root<X> getRoot( CriteriaQuery<X> query )
    {
        return query.from( classe );
    }
    
    protected abstract Predicate[] filtrar( T filtro, CriteriaBuilder builder, Root<X> from );
}
