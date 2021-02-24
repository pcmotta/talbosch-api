package br.com.attomtech.talbosch.api.repository.paginacao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Model;

public abstract class PaginacaoOrdenacao<T extends Model>
{
    protected void adicionarPaginacao( TypedQuery<? extends Model> typed, Pageable pageable )
    {
        int paginaAtual              = pageable.getPageNumber( );
        int totalRegistrosPorPagina  = pageable.getPageSize( );
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        
        typed.setFirstResult( primeiroRegistroDaPagina );
        typed.setMaxResults( totalRegistrosPorPagina );
    }
    
    protected void adicionarOrdenacao( Pageable pageable, CriteriaBuilder builder, CriteriaQuery<? extends Model> query, Root<T> root, String orderDefault )
    {
        adicionarOrdenacao( pageable, builder, query, root, orderDefault, true );
    }
    
    protected void adicionarOrdenacao( Pageable pageable, CriteriaBuilder builder, CriteriaQuery<? extends Model> query, Root<T> root, String orderDefault, boolean isAsc )
    {
        Sort sort = pageable.getSort( );
        
        if( sort != null && sort.iterator( ).hasNext( ) )
        {
            Path<Object> path = null;
            
            Sort.Order order = sort.iterator( ).next( );
            
            String property = order.getProperty( );
            if( property.contains( "." ) )
            {
                String[] properties = property.split( "\\." );
                Join<Object, Object> join = root.join( properties[0] );
                
                path = join.get( properties[1] );
            }
            else
                path = root.get( property );
            
            query.orderBy( order.isAscending( ) ? builder.asc( path ) : builder.desc( path ) );
        }
        else if( StringUtils.hasText( orderDefault ) )
            query.orderBy( isAsc ? builder.asc( root.get( orderDefault ) ) : builder.desc( root.get( orderDefault ) ) );
    }
}
