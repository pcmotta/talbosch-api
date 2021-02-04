package br.com.attomtech.talbosch.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;
import br.com.attomtech.talbosch.api.repository.query.ClienteRepositoryQuery;

public class ClienteRepositoryImpl extends RepositoryImpl<ClienteFilter, Cliente> implements ClienteRepositoryQuery
{
    @Override
    public Page<Cliente> pesquisar( ClienteFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, "nome" );
    }

    @Override
    protected Predicate[] filtrar( ClienteFilter filtro, CriteriaBuilder builder, Root<Cliente> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        Join<Object, Object> endereco = null;
        
        if( filtro.getCodigo( ) != null )
            predicates.add( builder.equal( from.get( ClienteFilter.CODIGO ), filtro.getCodigo( ) ) );
        
        if( StringUtils.hasText( filtro.getNome( ) ) )
            predicates.add( builder.like( builder.lower( from.get( ClienteFilter.NOME ) ), "%" + filtro.getNome( ).toLowerCase( ) + "%" ) );
        
        if( filtro.getCpfCnpj( ) != null )
            predicates.add( builder.equal( from.get( ClienteFilter.CPFCNPJ ), filtro.getCpfCnpj( ) ) );
        
        if( filtro.getTipoPessoa( ) != null )
            predicates.add( builder.equal( from.get( ClienteFilter.TIPOPESSOA ), filtro.getTipoPessoa( ) ) );
        
        if( StringUtils.hasText( filtro.getEmail( ) ) )
            predicates.add( builder.like( builder.lower( from.get( ClienteFilter.EMAIL ) ), "%" + filtro.getEmail( ).toLowerCase( ) + "%" ) );
        
        if( filtro.getBandeiraVermelha( ) != null )
            predicates.add( builder.equal( from.get( ClienteFilter.BANDEIRAVERMELHA ), filtro.getBandeiraVermelha( ) ) );
        
        if( StringUtils.hasText( filtro.getLogradouro( ) ) )
        {
            endereco = from.join( ClienteFilter.ENDERECO );
            predicates.add( builder.like( builder.lower( endereco.get( ClienteFilter.LOGRADOURO ) ), "%" + filtro.getLogradouro( ).toLowerCase( ) + "%" ) );
        }
        
        if( StringUtils.hasText( filtro.getBairro( ) ) )
        {
            if( endereco == null )
                endereco = from.join( ClienteFilter.ENDERECO );
            
            predicates.add( builder.like( builder.lower( endereco.get( ClienteFilter.BAIRRO ) ), "%" + filtro.getBairro( ).toLowerCase( ) + "%" ) );
        }
        
        if( filtro.getCep( ) != null )
        {
            if( endereco == null )
                endereco = from.join( ClienteFilter.ENDERECO );
            
            predicates.add( builder.equal( endereco.get( ClienteFilter.CEP ), filtro.getCep( ) ) );
        }
        
        if( filtro.getNumeroTelefone( ) != null )
        {
            Join<Object, Object> telefone = from.join( ClienteFilter.TELEFONE );
            predicates.add( builder.equal( telefone.get( ClienteFilter.NUMEROTELEFONE ), filtro.getNumeroTelefone( ) ) );
        }
        
        predicates.add( builder.equal( from.get( ClienteFilter.ATIVO ), true ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }

}
