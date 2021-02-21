package br.com.attomtech.talbosch.api.repository.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.LogAtividades;
import br.com.attomtech.talbosch.api.repository.filter.LogAtividadesFilter;
import br.com.attomtech.talbosch.api.repository.query.LogAtividadesRepositoryQuery;

public class LogAtividadesRepositoryImpl extends RepositoryImpl<LogAtividadesFilter, LogAtividades> implements LogAtividadesRepositoryQuery
{
    @Override
    public Page<LogAtividades> pesquisar( LogAtividadesFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, "data", false );
    }

    @Override
    protected Predicate[] filtrar( LogAtividadesFilter filtro, CriteriaBuilder builder, Root<LogAtividades> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( filtro.getAcao( ) != null )
            predicates.add( builder.equal( from.get( LogAtividadesFilter.ACAO ), filtro.getAcao( ) ) );
        
        if( filtro.getArea( ) != null )
            predicates.add( builder.equal( from.get( LogAtividadesFilter.AREA ), filtro.getArea( ) ) );
        
        if( StringUtils.hasText( filtro.getCodigoObjeto( ) ) )
        {
            try
            {
                Long codigoObjeto = Long.valueOf( filtro.getCodigoObjeto( ) );
                
                predicates.add( builder.or( builder.equal( from.get( LogAtividadesFilter.CODIGOOBJETO ), codigoObjeto ),
                                            builder.equal( from.get( LogAtividadesFilter.CODIGOPECA ), filtro.getCodigoObjeto( ) ) ) );
            }
            catch( NumberFormatException e )
            {
                predicates.add( builder.equal( from.get( LogAtividadesFilter.CODIGOPECA ), filtro.getCodigoObjeto( ) ) );
            }
        }
        
        if( filtro.getDataDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( LogAtividadesFilter.DATA ), LocalDateTime.of( filtro.getDataDe( ), LocalTime.MIDNIGHT ) ) );
        
        if( filtro.getDataAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( LogAtividadesFilter.DATA ), LocalDateTime.of( filtro.getDataAte( ), LocalTime.of( 23, 59 ) ) ) );
        
        if( filtro.getUsuario( ) != null )
            predicates.add( builder.equal( from.get( LogAtividadesFilter.USUARIO ), filtro.getUsuario( ) ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}
