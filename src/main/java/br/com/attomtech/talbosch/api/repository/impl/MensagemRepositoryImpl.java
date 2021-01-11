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

import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.repository.filter.MensagemFilter;
import br.com.attomtech.talbosch.api.repository.query.MensagemRepositoryQuery;

public class MensagemRepositoryImpl extends RepositoryImpl<MensagemFilter, Mensagem> implements MensagemRepositoryQuery
{
    @Override
    public Page<Mensagem> pesquisar( MensagemFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, MensagemFilter.DATA, false );
    }

    @Override
    protected Predicate[] filtrar( MensagemFilter filtro, CriteriaBuilder builder, Root<Mensagem> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        predicates.add( 
                builder.or( 
                        builder.and( builder.equal( from.get( MensagemFilter.USUARIODESTINO ), filtro.getUsuarioLogado( ) ),
                                     builder.equal( from.get( MensagemFilter.DELETADODESTINO ), false ) ),
                        builder.and( builder.equal( from.get( MensagemFilter.USUARIOORIGEM ), filtro.getUsuarioLogado( ) ),
                                     builder.equal( from.get( MensagemFilter.DELETADOORIGEM ), false ) ) ) );
        
        if( filtro.getUsuarioDestino( ) != null )
            predicates.add( builder.equal( from.get( MensagemFilter.USUARIODESTINO ), filtro.getUsuarioDestino( ) ) );
        
        if( filtro.getUsuarioOrigem( ) != null )
            predicates.add( builder.equal( from.get( MensagemFilter.USUARIOORIGEM ), filtro.getUsuarioOrigem( ) ) );
        
        if( filtro.getDataDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( MensagemFilter.DATA ), LocalDateTime.of( filtro.getDataDe( ), LocalTime.MIDNIGHT ) ) );
        
        if( filtro.getDataAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( MensagemFilter.DATA ), LocalDateTime.of( filtro.getDataAte( ), LocalTime.of( 23, 59 ) ) ) );
        
        if( filtro.getLido( ) != null )
            predicates.add( builder.equal( from.get( MensagemFilter.LIDO ), filtro.getLido( ) ) );
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }

}
