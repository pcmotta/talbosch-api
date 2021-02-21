package br.com.attomtech.talbosch.api.repository.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;
import br.com.attomtech.talbosch.api.repository.query.OrdemServicoRepositoryQuery;

public class OrdemServicoRepositoryImpl extends RepositoryImpl<OrdemServicoFilter, OrdemServico> implements OrdemServicoRepositoryQuery
{
    @Override
    public Page<OrdemServico> pesquisar( OrdemServicoFilter filtro, Pageable pageable )
    {
        return pesquisarDados( filtro, pageable, "numero", false );
    }

    @Override
    protected Predicate[] filtrar( OrdemServicoFilter filtro, CriteriaBuilder builder, Root<OrdemServico> from )
    {
        List<Predicate> predicates = new ArrayList<Predicate>( );
        
        if( filtro.getNumero( ) != null )
            predicates.add( builder.equal( from.get( OrdemServicoFilter.NUMERO ), filtro.getNumero( ) ) );
        
        if( filtro.getCliente( ) != null )
            predicates.add( builder.equal( from.get( OrdemServicoFilter.CLIENTE ), filtro.getCliente( ) ) );
        
        if( filtro.getTipo( ) != null )
            predicates.add( builder.equal( from.get( OrdemServicoFilter.TIPO ), filtro.getTipo( ) ) );
        
        if( filtro.getDataChamadaDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( OrdemServicoFilter.DATACHAMADA ), filtro.getDataChamadaDe( ) ) );
        
        if( filtro.getDataChamadaAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( OrdemServicoFilter.DATACHAMADA ), filtro.getDataChamadaAte( ) ) );
        
        if( filtro.getDataAtendimentoDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( OrdemServicoFilter.DATAATENDIMENTO ), filtro.getDataAtendimentoDe( ) ) );
        
        if( filtro.getDataAtendimentoAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( OrdemServicoFilter.DATAATENDIMENTO ), filtro.getDataAtendimentoAte( ) ) );
        
        if( filtro.getDataBaixaDe( ) != null )
            predicates.add( builder.greaterThanOrEqualTo( from.get( OrdemServicoFilter.DATABAIXA ), LocalDateTime.of( filtro.getDataBaixaDe( ), LocalTime.MIDNIGHT ) ) );
        
        if( filtro.getDataBaixaAte( ) != null )
            predicates.add( builder.lessThanOrEqualTo( from.get( OrdemServicoFilter.DATABAIXA ), LocalDateTime.of( filtro.getDataBaixaAte( ), LocalTime.of( 23, 59 ) ) ) );
        
        if( filtro.getTecnico( ) != null )
        {
            Join<Object, Object> atendimentos = from.join( "atendimentos", JoinType.LEFT );
            
            predicates.add( builder.or( builder.equal( from.get( OrdemServicoFilter.TECNICO ), filtro.getTecnico( ) ),
                    builder.equal( atendimentos.get( OrdemServicoFilter.TECNICO ), filtro.getTecnico( ) ) ) );
        }
        
        if( filtro.getStatus( ) != null )
            predicates.add( builder.equal( from.get( OrdemServicoFilter.STATUS ), filtro.getStatus( ) ) );
        
        Join<Object, Object> endereco = null;
        if( StringUtils.hasText( filtro.getLogradouro( ) ) )
        {
            endereco = from.join( OrdemServicoFilter.ENDERECO );
            predicates.add( builder.like( builder.lower( endereco.get( OrdemServicoFilter.LOGRADOURO ) ), "%" + filtro.getLogradouro( ).toLowerCase( ) + "%" ) );
        }
        
        if( StringUtils.hasText( filtro.getBairro( ) ) )
        {
            if( endereco == null )
                endereco = from.join( OrdemServicoFilter.ENDERECO );
            
            predicates.add( builder.like( builder.lower( endereco.get( OrdemServicoFilter.BAIRRO ) ), "%" + filtro.getBairro( ).toLowerCase( ) + "%" ) );
        }
        
        Join<Object, Object> produto = null;
        if( filtro.getAparelho( ) != null )
        {
            produto = from.join( OrdemServicoFilter.PRODUTO );
            predicates.add( builder.equal( produto.get( OrdemServicoFilter.APARELHO ), filtro.getAparelho( ) ) );
        }
        
        if( filtro.getFabricante( ) != null )
        {
            if( produto == null )
                produto = from.join( OrdemServicoFilter.PRODUTO );
            
            predicates.add( builder.equal( produto.get( OrdemServicoFilter.FABRICANTE ), filtro.getFabricante( ) ) );
        }
        
        return predicates.toArray( new Predicate[predicates.size( )] );
    }
}
