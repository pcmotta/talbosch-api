package br.com.attomtech.talbosch.api.dto;

import br.com.attomtech.talbosch.api.model.Peca;

public class PecaDTO
{
    private String  codigo;
    private String  descricao;
    private boolean ativo;

    public PecaDTO( Peca peca )
    {
        this.codigo = peca.getCodigo( );
        this.descricao = peca.getDescricao( );
        this.ativo = peca.isAtivo( );
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }

    public String getDescricao( )
    {
        return descricao;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }
}
