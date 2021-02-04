package br.com.attomtech.talbosch.api.dto;

import br.com.attomtech.talbosch.api.model.Usuario;

public class UsuarioDTO
{
    private Long    codigo;
    private String  nome;
    private boolean ativo;

    public UsuarioDTO( Usuario usuario )
    {
        this.codigo = usuario.getCodigo( );
        this.nome = usuario.getNome( );
        this.ativo = usuario.isAtivo( );
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getNome( )
    {
        return nome;
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

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }
}
