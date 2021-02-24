package br.com.attomtech.talbosch.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.OrdemEndereco;
import br.com.attomtech.talbosch.api.model.OrdemProduto;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;
import br.com.attomtech.talbosch.api.utils.Utils;

public class OrdemServicoRelatorioDTO
{
    private LocalDate     dataChamada;
    private LocalDate     dataAtendimento;
    private TipoOrdem     classificacao;
    private Usuario       atendente;
    private Cliente       cliente;
    private String        telefones;
    private OrdemEndereco endereco;
    private OrdemProduto  produto;
    private List<OrdemServicoRelatorioTabelaDTO> pecasServicos = new ArrayList<OrdemServicoRelatorioTabelaDTO>( );
    private BigDecimal    total;
    private String        observacao;
    
    public OrdemServicoRelatorioDTO( OrdemServico ordem )
    {
        this.dataChamada = ordem.getDataChamada( );
        this.classificacao = ordem.getTipo( );
        this.atendente = ordem.getAtendente( );
        this.cliente = ordem.getCliente( );
        this.telefones =  ordem.getCliente( ).getTelefones( ).stream( )
                .map( t -> Utils.formatarTelefone( t.getNumero( ) ) )
                .collect( Collectors.joining( " | " ) );
        this.endereco = ordem.getEndereco( );
        this.produto = ordem.getProduto( );
        this.observacao = ordem.getObservacao( );

        this.dataAtendimento = ordem.getDataAtendimento( ) != null ? ordem.getDataAtendimento( ) :
            ordem.getAtendimentos( ).stream( ).map( a -> a.getDataAtendimento( ) ).min( (a1, a2) -> a1.compareTo( a2 ) ).get( );
        
        ordem.getPecas( ).stream( ).collect( Collectors.groupingBy( p -> p.getPeca( ) ) )
            .forEach( (peca, pecas) -> {
                this.pecasServicos.add( new OrdemServicoRelatorioTabelaDTO( pecas.size( ), peca.getCodigo( ), 
                        peca.getDescricao( ), peca.getValor( ) ) );
            });
        
        ordem.getValores( ).forEach( valor -> this.pecasServicos.add( new OrdemServicoRelatorioTabelaDTO( valor.getTipo( ).getDescricao( ), valor.getValor( ) ) ));
        IntStream.range( 0, 10 - pecasServicos.size( ) ).forEach( index -> pecasServicos.add( new OrdemServicoRelatorioTabelaDTO( ) ) );
        
        this.total = this.pecasServicos.stream( ).filter( p -> p.getValor( ) != null && p.getQuantidade( ) != null )
                .map( p -> p.getTotal( ) ).reduce( BigDecimal.ZERO, BigDecimal::add );
    }

    public LocalDate getDataChamada( )
    {
        return dataChamada;
    }

    public LocalDate getDataAtendimento( )
    {
        return dataAtendimento;
    }

    public TipoOrdem getClassificacao( )
    {
        return classificacao;
    }

    public Usuario getAtendente( )
    {
        return atendente;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public String getTelefones( )
    {
        return telefones;
    }

    public OrdemEndereco getEndereco( )
    {
        return endereco;
    }

    public OrdemProduto getProduto( )
    {
        return produto;
    }

    public List<OrdemServicoRelatorioTabelaDTO> getPecasServicos( )
    {
        return pecasServicos;
    }

    public String getObservacao( )
    {
        return observacao;
    }

    public void setDataChamada( LocalDate dataChamada )
    {
        this.dataChamada = dataChamada;
    }

    public void setDataAtendimento( LocalDate dataAtendimento )
    {
        this.dataAtendimento = dataAtendimento;
    }

    public void setClassificacao( TipoOrdem classificacao )
    {
        this.classificacao = classificacao;
    }

    public void setAtendente( Usuario atendente )
    {
        this.atendente = atendente;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public void setTelefones( String telefones )
    {
        this.telefones = telefones;
    }

    public void setEndereco( OrdemEndereco endereco )
    {
        this.endereco = endereco;
    }

    public void setProduto( OrdemProduto produto )
    {
        this.produto = produto;
    }

    public void setPecasServicos( List<OrdemServicoRelatorioTabelaDTO> pecasServicos )
    {
        this.pecasServicos = pecasServicos;
    }

    public void setObservacao( String observacao )
    {
        this.observacao = observacao;
    }

    public BigDecimal getTotal( )
    {
        return total;
    }

    public void setTotal( BigDecimal total )
    {
        this.total = total;
    }
}
