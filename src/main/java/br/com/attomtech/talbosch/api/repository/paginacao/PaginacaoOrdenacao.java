package br.com.attomtech.talbosch.api.repository.paginacao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public abstract class PaginacaoOrdenacao<T>
{
    protected void adicionarPaginacao( TypedQuery<T> typed, Pageable pageable )
    {
        int paginaAtual              = pageable.getPageNumber( );
        int totalRegistrosPorPagina  = pageable.getPageSize( );
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        
        typed.setFirstResult( primeiroRegistroDaPagina );
        typed.setMaxResults( totalRegistrosPorPagina );
    }
    
    protected void adicionarOrdenacao( Pageable pageable, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root, String orderDefault )
    {
        adicionarOrdenacao( pageable, builder, query, root, orderDefault, true );
    }
    
    protected void adicionarOrdenacao( Pageable pageable, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root, String orderDefault, boolean isAsc )
    {
        Sort sort = pageable.getSort( );
        
        if( sort != null && sort.iterator( ).hasNext( ) )
        {
            Sort.Order order = sort.iterator( ).next( );
            Path<Object> property = root.get( order.getProperty( ) );
            
            query.orderBy( order.isAscending( ) ? builder.asc( property ) : builder.desc( property ) );
        }
        else if( StringUtils.hasText( orderDefault ) )
            query.orderBy( isAsc ? builder.asc( root.get( orderDefault ) ) : builder.desc( root.get( orderDefault ) ) );
    }
}
