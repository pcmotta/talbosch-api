package br.com.attomtech.talbosch.api.dto;

import java.math.BigDecimal;

public class OrdemServicoRelatorioTabelaDTO
{
    private Integer    quantidade;
    private String     codigo;
    private String     descricao;
    private BigDecimal valor;
    private BigDecimal total;
    
    public OrdemServicoRelatorioTabelaDTO( )
    {
        this.quantidade = null;
        this.codigo = null;
        this.descricao = null;
        this.valor = null;
        this.total = null;
    }
    
    public OrdemServicoRelatorioTabelaDTO( String descricao, BigDecimal valor )
    {
        this.quantidade = 1;
        this.codigo = null;
        this.descricao = descricao;
        this.valor =  valor;
        this.total = valor;
    }
    
    public OrdemServicoRelatorioTabelaDTO( int quantidade, String codigo, String descricao, BigDecimal valor )
    {
        this.quantidade = quantidade;
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor =  valor;
        this.total = valor.multiply( BigDecimal.valueOf( quantidade ) );
    }

    public Integer getQuantidade( )
    {
        return quantidade;
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public String getDescricao( )
    {
        return descricao;
    }

    public BigDecimal getValor( )
    {
        return valor;
    }

    public void setQuantidade( Integer quantidade )
    {
        this.quantidade = quantidade;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }

    public void setValor( BigDecimal valor )
    {
        this.valor = valor;
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
