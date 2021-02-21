package br.com.attomtech.talbosch.api.model.abstracts;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;

@MappedSuperclass
public abstract class Produto implements Cloneable
{
    @Enumerated(EnumType.STRING)
    protected Aparelho aparelho;

    @Enumerated(EnumType.STRING)
    protected Fabricante fabricante;
    protected String     notaFiscal;
    protected LocalDate  emissaoNotaFiscal;
    protected String     cor;
    protected String     modelo;
    protected String     fabSerie;
    protected String     modeloEvaporadora;
    protected String     fabSerieEvaporadora;
    protected Integer    tensao;
    protected String     revendedor;

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

    public String getNotaFiscal( )
    {
        return notaFiscal;
    }

    public void setNotaFiscal( String notaFiscal )
    {
        this.notaFiscal = notaFiscal;
    }

    public LocalDate getEmissaoNotaFiscal( )
    {
        return emissaoNotaFiscal;
    }

    public void setEmissaoNotaFiscal( LocalDate emissaoNotaFiscal )
    {
        this.emissaoNotaFiscal = emissaoNotaFiscal;
    }

    public String getCor( )
    {
        return cor;
    }

    public void setCor( String cor )
    {
        this.cor = cor;
    }

    public String getModelo( )
    {
        return modelo;
    }

    public void setModelo( String modelo )
    {
        this.modelo = modelo;
    }

    public String getFabSerie( )
    {
        return fabSerie;
    }

    public void setFabSerie( String fabSerie )
    {
        this.fabSerie = fabSerie;
    }

    public String getModeloEvaporadora( )
    {
        return modeloEvaporadora;
    }

    public void setModeloEvaporadora( String modeloEvaporadora )
    {
        this.modeloEvaporadora = modeloEvaporadora;
    }

    public String getFabSerieEvaporadora( )
    {
        return fabSerieEvaporadora;
    }

    public void setFabSerieEvaporadora( String fabSerieEvaporadora )
    {
        this.fabSerieEvaporadora = fabSerieEvaporadora;
    }

    public Integer getTensao( )
    {
        return tensao;
    }

    public void setTensao( Integer tensao )
    {
        this.tensao = tensao;
    }

    public String getRevendedor( )
    {
        return revendedor;
    }

    public void setRevendedor( String revendedor )
    {
        this.revendedor = revendedor;
    }
}
