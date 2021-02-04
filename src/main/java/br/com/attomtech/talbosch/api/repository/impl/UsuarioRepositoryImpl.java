package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.filter.UsuarioFilter;
import br.com.attomtech.talbosch.api.repository.query.UsuarioRepositoryQuery;

public class UsuarioRepositoryImpl extends RepositoryImpl<UsuarioFilter, Usuario> implements UsuarioRepositoryQuery
{
    @Override
    public Page<Usuario> pesquisar( UsuarioFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, UsuarioFilter.NOME );
    }

    @Override
    protected Predicate[] filtrar( UsuarioFilter filtro, CriteriaBuilder builder, Root<Usuario> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( StringUtils.hasText( filtro.getNome( ) ) )
            predicates.add( builder.like( builder.lower( from.get( UsuarioFilter.NOME ) ), "%" + filtro.getNome( ).toLowerCase( ) + "%" ) );
        
        if( filtro.isAtivo( ) != null )
            predicates.add( builder.equal( from.get( UsuarioFilter.ATIVO ), filtro.isAtivo( ) ) );
        
        predicates.add( builder.notEqual( from.get( UsuarioFilter.CODIGO ), 1 ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
    
}
