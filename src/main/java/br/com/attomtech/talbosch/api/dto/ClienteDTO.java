package br.com.attomtech.talbosch.api.dto;

import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;

public class ClienteDTO
{
    private Long       codigo;
    private String     nome;
    private Long       documento;
    private TipoPessoa tipo;
    private boolean    ativo;

    public ClienteDTO( Long codigo, String nome, Long documento, TipoPessoa tipo, boolean ativo )
    {
        this.codigo = codigo;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getNome( )
    {
        return nome;
    }

    public Long getDocumento( )
    {
        return documento;
    }

    public TipoPessoa getTipo( )
    {
        return tipo;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public void setDocumento( Long documento )
    {
        this.documento = documento;
    }

    public void setTipo( TipoPessoa tipo )
    {
        this.tipo = tipo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }
}
