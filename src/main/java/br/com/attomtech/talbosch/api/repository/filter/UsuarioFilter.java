package br.com.attomtech.talbosch.api.repository.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioFilter
{
    public static final String NOME  = "nome",
                               ATIVO = "ativo",
                               CODIGO = "codigo";
    
    private String  nome;
    private Boolean ativo;
    
    @JsonIgnore
    private Long codigo;

    public String getNome( )
    {
        return nome;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public Boolean isAtivo( )
    {
        return ativo;
    }

    public void setAtivo( Boolean ativo )
    {
        this.ativo = ativo;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }
}
