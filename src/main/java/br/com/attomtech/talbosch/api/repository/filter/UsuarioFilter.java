package br.com.attomtech.talbosch.api.repository.filter;

public class UsuarioFilter
{
    public static final String NOME  = "nome",
                               ATIVO = "ativo";
    
    private String  nome;
    private Boolean ativo;

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
}
