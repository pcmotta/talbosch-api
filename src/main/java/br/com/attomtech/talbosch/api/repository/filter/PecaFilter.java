package br.com.attomtech.talbosch.api.repository.filter;

import java.math.BigDecimal;

import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;

public class PecaFilter
{
    public static final String CODIGO          = "codigo",
                               DESCRICAO       = "descricao",
                               VALOR           = "valor",
                               VALORTECNICO    = "valorTecnico",
                               VALORMAODEOBRA  = "valorMaoDeObra",
                               MODELO          = "modelos",
                               APARELHO        = "aparelho",
                               FABRICANTE      = "fabricante",
                               ATIVO           = "ativo";
    
    private String     codigo;
    private String     descricao;
    private BigDecimal valorDe;
    private BigDecimal valorAte;
    private BigDecimal valorTecnicoDe;
    private BigDecimal valorTecnicoAte;
    private BigDecimal valorMaoDeObraDe;
    private BigDecimal valorMaoDeObraAte;
    private String     modelo;
    private Aparelho   aparelho;
    private Fabricante fabricante;
    private Boolean    ativo;

    public String getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public String getDescricao( )
    {
        return descricao;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }

    public BigDecimal getValorDe( )
    {
        return valorDe;
    }

    public void setValorDe( BigDecimal valorDe )
    {
        this.valorDe = valorDe;
    }

    public BigDecimal getValorAte( )
    {
        return valorAte;
    }

    public void setValorAte( BigDecimal valorAte )
    {
        this.valorAte = valorAte;
    }

    public BigDecimal getValorTecnicoDe( )
    {
        return valorTecnicoDe;
    }

    public void setValorTecnicoDe( BigDecimal valorTecnicoDe )
    {
        this.valorTecnicoDe = valorTecnicoDe;
    }

    public BigDecimal getValorTecnicoAte( )
    {
        return valorTecnicoAte;
    }

    public void setValorTecnicoAte( BigDecimal valorTecnicoAte )
    {
        this.valorTecnicoAte = valorTecnicoAte;
    }

    public BigDecimal getValorMaoDeObraDe( )
    {
        return valorMaoDeObraDe;
    }

    public void setValorMaoDeObraDe( BigDecimal valorMaoDeObraDe )
    {
        this.valorMaoDeObraDe = valorMaoDeObraDe;
    }

    public BigDecimal getValorMaoDeObraAte( )
    {
        return valorMaoDeObraAte;
    }

    public void setValorMaoDeObraAte( BigDecimal valorMaoDeObraAte )
    {
        this.valorMaoDeObraAte = valorMaoDeObraAte;
    }

    public String getModelo( )
    {
        return modelo;
    }

    public void setModelo( String modelo )
    {
        this.modelo = modelo;
    }

    public Aparelho getAparelho( )
    {
        return aparelho;
    }

    public void setAparelho( Aparelho aparelho )
    {
        this.aparelho = aparelho;
    }

    public Fabricante getFabricante( )
    {
        return fabricante;
    }

    public void setFabricante( Fabricante fabricante )
    {
        this.fabricante = fabricante;
    }
    
    public Boolean getAtivo( )
    {
        return ativo;
    }

    public void setAtivo( Boolean ativo )
    {
        this.ativo = ativo;
    }

    @Override
    public String toString( )
    {
        return "PecaFilter [codigo=" + codigo + ", descricao=" + descricao + ", valorDe=" + valorDe + ", valorAte="
                + valorAte + ", valorTecnicoDe=" + valorTecnicoDe + ", valorTecnicoAte=" + valorTecnicoAte
                + ", valorMaoDeObraDe=" + valorMaoDeObraDe + ", valorMaoDeObraAte=" + valorMaoDeObraAte + ", modelo="
                + modelo + ", aparelho=" + aparelho + ", fabricante=" + fabricante + "]";
    }
}
