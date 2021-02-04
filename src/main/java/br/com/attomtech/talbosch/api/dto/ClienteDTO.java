package br.com.attomtech.talbosch.api.dto;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;

public class ClienteDTO
{
    private Long       codigo;
    private String     nome;
    private Long       documento;
    private TipoPessoa tipo;
    private boolean    ativo;

    public ClienteDTO( Cliente cliente )
    {
        this.codigo = cliente.getCodigo( );
        this.nome = cliente.getNome( );
        this.documento = cliente.getCpfCnpj( );
        this.tipo = cliente.getTipoPessoa( );
        this.ativo = cliente.isAtivo( );
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
