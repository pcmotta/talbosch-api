package br.com.attomtech.talbosch.api.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.repository.paginacao.PaginacaoOrdenacao;

public abstract class RepositoryImplSuper <T, X extends Model, D extends Model> extends PaginacaoOrdenacao<X>
{
    @PersistenceContext
    protected EntityManager manager;
    protected Class<X> classe;
    protected Class<D> classeDto;
    
    @SuppressWarnings("unchecked")
    protected RepositoryImplSuper( )
    {
        ParameterizedType type = (ParameterizedType)getClass( ).getGenericSuperclass( );
        Type[] actualTypeArguments = type.getActualTypeArguments( );
        
        this.classe = (Class<X>)actualTypeArguments[1];
        
        if( actualTypeArguments.length > 2 )
            this.classeDto = (Class<D>)actualTypeArguments[2];
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
